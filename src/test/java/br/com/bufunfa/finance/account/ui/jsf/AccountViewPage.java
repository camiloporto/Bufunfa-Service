package br.com.bufunfa.finance.account.ui.jsf;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.AbstractViewPage;

public class AccountViewPage extends AbstractViewPage {
	
	class AccountDialogPanel {
		public void fillAndSubmitDialogForm(String name, String desc) {
			fillInputElement(accountViewIds.getInputNewAccountName(), name);
			fillInputElement(accountViewIds.getInputNewAccountDesc(), desc);
			getSaveAccountButton().click();
		}
		
		public WebElement getSaveAccountButton() {
			return findWebElementById(accountViewIds.getButtonSaveAccount());
		}
	}
	
	private AccountViewNames accountViewIds;
	
	public AccountViewPage(WebDriver driver, AccountViewNames viewNames) {
		this.driver = driver;
		this.accountViewIds = viewNames;
	}
	

	public void assertThatUserInfoIsOnThePage(String userId) {
		WebElement userInfoPanel = findWebElementById(accountViewIds.getPanelUserinfo());
		String userInfoText = userInfoPanel.getText();
		
		Assert.assertTrue("user info " + userId + " not found on page", 
				userInfoText.contains(userId));
	}
	
	@Override
	protected String getPanelMessageId() {
		return accountViewIds.getPanelMessages();
	}
	
	@Override
	protected String getPageTitle() {
		return accountViewIds.getTitlePage();
	}
	
	@Override
	protected String getPageTitleElementId() {
		return accountViewIds.getTitleId();
	}
	
	public void assertThatInfoMessageIsPresent(String testMsg, String expectedInfoMessage) {
		WebElement growlContainer = findWebElementById("growl_container");
		List<WebElement> messages = growlContainer.findElements(By.xpath(".//span"));
		boolean found = false;
		for (WebElement webElement : messages) {
			if(expectedInfoMessage.equalsIgnoreCase(webElement.getText())) {
				found = true;
			}
		}
		Assert.assertTrue(testMsg, found);
	}

	public AccountDialogPanel getAccountDialogPanel() {
		return new AccountDialogPanel();
	}
	
	public WebElement getDivOfAccount(String accountName) {
		List<WebElement> divs = findWebElementByXPath("//div[@class='accountItemClass']");
		for (WebElement webElement : divs) {
			if(webElement.getText().equalsIgnoreCase(accountName)) {
				return webElement;
			}
		}
		return null;
	}
	
	public WebElement getAddAccountLinkOfAccount(WebElement accountDiv) {
		List<WebElement> actionLinks = accountDiv.findElements(By.xpath(".//a"));
		for (WebElement a : actionLinks) {
			if("adicionar".equalsIgnoreCase(a.getText())){
				return a;
			}
		}
		return null;
	}
	
	public WebElement getActionLinkOfAccountByName(WebElement accountDiv, String actionLinkText) {
		List<WebElement> actionLinks = accountDiv.findElements(By.xpath(".//a"));
		for (WebElement a : actionLinks) {
			if(actionLinkText.equalsIgnoreCase(a.getText())){
				return a;
			}
		}
		return null;
	}
	
	public void addNewAccount(String fatherName, String newAccountName, String newAccountDesc) {
		WebElement fatherDiv = getDivOfAccount(fatherName);
		Actions builder = new Actions(getDriver());
		builder.moveToElement(fatherDiv).build().perform();
		wait(1);
		
		getAddAccountLinkOfAccount(fatherDiv).click();
		wait(1);
		
		getAccountDialogPanel().fillAndSubmitDialogForm(newAccountName, newAccountDesc);
	}

	public void clickLinkLogoutUser() {
		WebElement logoutLink = findWebElementById(accountViewIds.getLinkLogout());
		
		//FIXME o click nao esta enviando o formulario e invocando o controller. com botao funciona!
		System.out.println("AccountViewPage.clickLinkLogoutUser()" + logoutLink.getText());
		System.out.println("AccountViewPage.clickLinkLogoutUser()" + logoutLink.getTagName());
		logoutLink.click();
		wait(10);
	}

}
