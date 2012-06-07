package br.com.bufunfa.finance.account.ui.jsf;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.AbstractViewPage;

public class AccountViewPage extends AbstractViewPage {
	
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


	public void clickLinkLogoutUser() {
		WebElement logoutLink = findWebElementById(accountViewIds.getLinkLogout());
		
		//FIXME o click nao esta enviando o formulario e invocando o controller. com botao funciona!
		System.out.println("AccountViewPage.clickLinkLogoutUser()" + logoutLink.getText());
		System.out.println("AccountViewPage.clickLinkLogoutUser()" + logoutLink.getTagName());
		logoutLink.click();
		wait(10);
	}

}
