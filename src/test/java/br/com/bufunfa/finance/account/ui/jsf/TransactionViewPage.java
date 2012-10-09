package br.com.bufunfa.finance.account.ui.jsf;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
			final String fromAccountElementId = "fromAccount";
			fillPrimefacesSelectOneMenu(value, fromAccountElementId);
		}
		
		public void fillToAccount(String value) {
			final String toAccountElementId = "toAccount";
			fillPrimefacesSelectOneMenu(value, toAccountElementId);
		}
		
		private void fillPrimefacesSelectOneMenu(String optionTextToSelect, String selectElementId) {
			WebElement fromAccountDiv = findWebElementById(selectElementId);
			List<WebElement> divs = fromAccountDiv.findElements(By.tagName("div"));
			WebElement selectArrow = divs.get(1);
			selectArrow.click();
			
			WebElement fromAccountPanelOptions = findWebElementById(selectElementId + "_panel");
			List<WebElement> options = fromAccountPanelOptions.findElements(By.tagName("li"));
			
			for (WebElement op : options) {
				if(optionTextToSelect.equalsIgnoreCase(op.getText())) {
					Actions builder = new Actions(getDriver());
					builder.moveToElement(op).build().perform();
					op.click();
					return;
				}
			}
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

	public void assertThatTransactionIsOnPage(String transactionComment) {
		WebElement transactionDiv = findWebElementById("transactionTable");
		List<WebElement> commentColumns = transactionDiv.findElements(By.xpath(".//tr/td[4]"));
		boolean found = false;
		for (WebElement webElement : commentColumns) {
			if(transactionComment.equals(webElement.getText())) {
				found = true;
				break;
			}
		}
		Assert.assertTrue("Transaction with comment " + transactionComment + " not found", found);
	}

}
