package br.com.bufunfa.finance.core.ui.jsf;

import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public abstract class AbstractViewPage {
	
	protected WebDriver driver;
	
	
	protected WebDriver getDriver() {
		return driver;
	}
	
	public void assertThatIsOnThePage() {
		WebElement title = findWebElementById(getPageTitleElementId());
		String actualTitle = title.getText();
		
		ExpectedCondition<Boolean> isOnPage = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver d) {
				WebElement title = d.findElement(By.id(getPageTitleElementId()));
				String actualTitle = title.getText();
				return getPageTitle().equalsIgnoreCase(actualTitle);
			}
		};
		waitAndCheckCondition(10, isOnPage, "page title is not equals: <expected> " + getPageTitle() + " <actual> " + actualTitle);
		
		Assert.assertEquals("page title is not equals", getPageTitle(), actualTitle);
	}
	
	protected void waitAndCheckCondition(int timeOutInSeconds, ExpectedCondition<Boolean> condition, String errorMessage) {
		try {
			(new WebDriverWait(driver, timeOutInSeconds)).until(condition);
		} catch (TimeoutException e) {
			Assert.fail(errorMessage);
		}
	}
	
	protected WebElement findWebElementById(String elementId) {
		return driver.findElement(By.id(elementId));
	}
	
	
	protected void fillInputElement(String elementId, String value) {
		WebElement inputField = findWebElementById(elementId);
		if(inputField == null) {
			throw new NoSuchElementException(elementId);
		}
		inputField.clear();
		inputField.sendKeys(value);
	}
	
	public void assertThatErrorMessagesArePresent(String...errorMessages) {
		WebElement divMessages = driver.findElement(By.id(getPanelMessageId()));
		List<WebElement> spans = divMessages.findElements(By.tagName("span"));
		
		for (String msg : errorMessages) {
			boolean found = false;
			for (WebElement webElement : spans) {
				String webElementText = webElement.getText();
				found |= webElementText.equalsIgnoreCase(msg);
			}
			Assert.assertTrue("expected message not found : " + msg, found);
		}
	}
	
	protected abstract String getPanelMessageId();
	
	protected abstract String getPageTitle();
	
	protected abstract String getPageTitleElementId();
}
