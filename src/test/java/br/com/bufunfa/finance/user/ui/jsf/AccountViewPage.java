package br.com.bufunfa.finance.user.ui.jsf;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.bufunfa.finance.user.ui.jsf.config.AccountViewNames;

public class AccountViewPage {
	
	private AccountViewNames accountViewIds;
	private WebDriver driver;
	
	public AccountViewPage(WebDriver driver, AccountViewNames viewNames) {
		this.driver = driver;
		this.accountViewIds = viewNames;
	}
	
	WebElement findWebElementById(String elementId) {
		return driver.findElement(By.id(elementId));
	}
	
	public void assertThatIsOnThePage() {
		WebElement title = findWebElementById(accountViewIds.getTitleId());
		String expectedPageTitle = accountViewIds.getTitlePage();
		String actualTitle = title.getText();
		
		Assert.assertEquals("page title is not equals", expectedPageTitle, actualTitle);
	}

}
