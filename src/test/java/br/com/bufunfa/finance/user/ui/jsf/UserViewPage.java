package br.com.bufunfa.finance.user.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.bufunfa.finance.account.ui.jsf.AccountViewPage;
import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.AbstractViewPage;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class UserViewPage extends AbstractViewPage {
	
	class UserForm {

		public void setEmail(String email) {
			fillInputElement(
					userViewIds.getInputUserEmail(), 
					email);
			
		}

		public void setPassword(String pass) {
			fillInputElement(
					userViewIds.getInputUserPassword(), 
					pass);
			
		}
	}


	private UserViewNames userViewIds;

	public UserViewPage(WebDriver driver, UserViewNames userViewNames) {
		this.userViewIds = userViewNames;
		this.driver = driver;
		getUserForm().setEmail("");
		getUserForm().setPassword("");
	}
	
	
	WebElement findWebElementByParentIdAndType(String parentId, String elementType) {
		WebElement parent = findWebElementById(parentId);
		return parent.findElement(By.xpath("//" + elementType + ""));
	}
	
	
	public void clickButtonAddNewUser() {
		WebElement addNewUserButton = findWebElementById(userViewIds.getButtonSaveNewUser());
		addNewUserButton.click();
	}
	
	
	public void assertThatAddNewUserSuccessMessageIsPresent() {
		WebElement divMessages = driver.findElement(By.id(userViewIds.getPanelMessages()));
		List<WebElement> spans = divMessages.findElements(By.tagName("span"));
		
		boolean found = false;
		String expectedMessage = userViewIds.getMessageUserAddedSuccess();//"Usuário adicionado com sucesso";
		for (WebElement webElement : spans) {
			String webElementText = webElement.getText();
			found |= expectedMessage.equalsIgnoreCase(webElementText);
		}
		Assert.assertTrue("expected message not found : " + expectedMessage, found);
	}
	
	public void assertThatNewUserFormIsEmpty() {
		WebElement inputUserEmail = driver.findElement(By.id(userViewIds.getInputUserEmail()));
		WebElement inputUserPass = driver.findElement(By.id(userViewIds.getInputUserPassword()));
		
		Assert.assertTrue("userForm::inputEmail is not empty", inputUserEmail.getAttribute("value").isEmpty());
		Assert.assertTrue("userForm::inputPassword is not empty", inputUserPass.getAttribute("value").isEmpty());
	}
	
	public void addNewUser(String newUserEmail, String newUserPass) {
		UserViewPage.UserForm userForm = getUserForm();
		
		userForm.setEmail(newUserEmail);
		userForm.setPassword(newUserPass);
		
		clickButtonAddNewUser();
		
	}
	
	@Override
	protected String getPanelMessageId() {
		return userViewIds.getPanelMessages();
	}
	
	@Override
	protected String getPageTitle() {
		return userViewIds.getTitlePage();
	}
	
	@Override
	protected String getPageTitleElementId() {
		return userViewIds.getTitleId();
	}
	
	public UserForm getUserForm() {
		findWebElementById(userViewIds.getFormUser());
		return new UserViewPage.UserForm();
	}

	public AccountViewPage clickButtonLoginUser() throws IOException, IllegalAccessException, InvocationTargetException {
		WebElement loginUserButton = findWebElementById(userViewIds.getButtonLoginUser());
		loginUserButton.click();
		AccountViewNames viewNames = new AccountViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				viewNames, 
				AccountViewNames.class.getCanonicalName());
		
		//FIXME esperar ate a pagina ser redicerionada
		return new AccountViewPage(driver, viewNames);
	}

}
