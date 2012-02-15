package br.com.bufunfa.finance.user.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.bufunfa.finance.user.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;
import br.com.bufunfa.finance.user.ui.jsf.util.Property2BeanUtil;

public class UserViewPage {
	
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
	private WebDriver driver;

	public UserViewPage(WebDriver driver, UserViewNames userViewNames) {
		this.userViewIds = userViewNames;
		this.driver = driver;
	}
	
	public void assertThatIsOnThePage() {
		WebElement title = findWebElementById(userViewIds.getTitleId());
		String expectedPageTitle = userViewIds.getTitlePage();
		String actualTitle = title.getText();
		
		Assert.assertEquals("page title is not equals", expectedPageTitle, actualTitle);
	}
	
	WebDriver getDriver() {
		return driver;
	}
	
	WebElement findWebElementById(String elementId) {
		return driver.findElement(By.id(elementId));
	}
	
	WebElement findWebElementByParentIdAndType(String parentId, String elementType) {
		WebElement parent = findWebElementById(parentId);
		return parent.findElement(By.xpath("//" + elementType + ""));
	}
	
	void fillInputElement(String elementId, String value) {
		WebElement inputField = findWebElementById(elementId);
		if(inputField == null) {
			throw new NoSuchElementException(elementId);
		}
		
		inputField.sendKeys(value);
	}
	
	public void clickButtonAddNewUser() {
		WebElement addNewUserButton = findWebElementById(userViewIds.getButtonSaveNewUser());
		addNewUserButton.click();
	}
	
	public void assertThatAddNewUserSuccessMessageIsPresent() {
		WebElement divMessages = driver.findElement(By.id(userViewIds.getPanelMessages()));
		List<WebElement> spans = divMessages.findElements(By.tagName("span"));
		
		boolean found = false;
		String expectedMessage = "usuario adicionado com sucesso";
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
