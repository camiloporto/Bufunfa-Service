package br.com.bufunfa.finance.account.ui.jsf;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.bufunfa.finance.account.ui.jsf.config.TransactionViewNames;
import br.com.bufunfa.finance.core.ui.jsf.AbstractViewPage;

public class TransactionViewPage extends AbstractViewPage {
	
	private TransactionViewNames transactionViewNames;
	
	public TransactionViewPage(WebDriver driver, TransactionViewNames transactionViewNames) {
		this.transactionViewNames = transactionViewNames;
		this.driver = driver;
	}

	@Override
	protected String getPanelMessageId() {
		return transactionViewNames.getPanelMessages();
	}

	@Override
	protected String getPageTitle() {
		return transactionViewNames.getTitlePage();
	}

	@Override
	protected String getPageTitleElementId() {
		return transactionViewNames.getTitleId();
	}
	
	public void clickButtonSaveTransaction() {
		WebElement saveTransactionButton = findWebElementById(transactionViewNames.getButtonSaveTransaction());
		saveTransactionButton.click();
	}
	
	public class TransactionForm {
		
		public void fillFromAccount(String value) {
			fillInputElement(
					transactionViewNames.getInputFromAccount(),
					value);
			WebElement uiItem = getDriver().findElement(By.xpath("//ui[@class='ui-autocomplete-item']"));
			uiItem.click();
		}
		
		public void fillToAccount(String value) {
			fillInputElement(
					transactionViewNames.getInputToAccount(),
					value);
			WebElement uiItem = getDriver().findElement(By.className("ui-autocomplete-item"));
			uiItem.click();
		}
		
		public void fillDate(String value) {
			fillInputElement(
					transactionViewNames.getInputDate(),
					value);
		}
		
		public void fillValue(String value) {
			fillInputElement(
					transactionViewNames.getInputValue(),
					value);
		}
		
		public void fillComment(String value) {
			fillInputElement(
					transactionViewNames.getInputComment(),
					value);
		}
		
	}

	public void assertThatTransactionIsPage(String comment) {
		Assert.fail("IMPLEMENT THIS OPERATION");		
	}

}
