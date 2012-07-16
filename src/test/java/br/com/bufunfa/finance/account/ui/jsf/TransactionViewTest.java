package br.com.bufunfa.finance.account.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.TransactionViewPage.TransactionForm;
import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.account.ui.jsf.config.TransactionViewNames;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.config.UserDataTestCleaner;
import br.com.bufunfa.finance.user.ui.jsf.UserViewPage;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class TransactionViewTest extends SpringRootTestsConfiguration {
	
	
	@Autowired
	private UserDataTestCleaner userDataCleaner;
	
	private UserViewNames userViewNames;
	
	private TransactionViewNames transactionViewNames;
	
	private AccountViewNames accountViewNames;
	
	private WebDriver webDriver;
	
	public TransactionViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		userViewNames = new UserViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				userViewNames, 
				UserViewNames.class.getCanonicalName());
		
		transactionViewNames = new TransactionViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				transactionViewNames, 
				TransactionViewNames.class.getCanonicalName());
		
		accountViewNames = new AccountViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				accountViewNames, 
				AccountViewNames.class.getCanonicalName());
		
		
	}
	
	@Before
	public void cleanUserData() {
		userDataCleaner.clearAllUsers();
		this.webDriver = getWebDriver("firefox");
	}
	
	@After
	public void afterTests() {
		this.webDriver.close();
	}
	
	@Test
	public void testAddNewTransaction_shouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		
		AccountViewPage accountPage = addNewUserAndLogin();
		String accountName1 = TestDataGenerator.generateRandomString();
		accountPage.addNewAccount(
				accountViewNames.getAssetAccountName(), 
				accountName1, TestDataGenerator.generateRandomString());
		accountPage.wait(1);
		
		String accountName2 = TestDataGenerator.generateRandomString();
		accountPage.addNewAccount(
				accountViewNames.getLiabilityAccountName(), 
				accountName2, TestDataGenerator.generateRandomString());
		accountPage.wait(1);
		
		TransactionViewPage page = goToTransactionPage();
		page.wait(1);
		
		final String date = "05/02/2012";
		final String value = "150";
		final String comment = TestDataGenerator.generateRandomString();
		
		TransactionForm form = page.new TransactionForm();
		form.fillFromAccount(accountName2);
		form.fillToAccount(accountName1);
		form.fillDate(date);
		form.fillValue(value);
		form.fillComment(comment);
		
		page.clickButtonSaveTransaction();
		page.wait(1);

		page.assertThatTransactionIsOnPage(comment);
		String expectedMessage = "Transação adicionada com sucesso";
		page.assertThatInfoMessageIsPresent(
				"save transaction: expectedMessage '" + expectedMessage + "' not found", 
				expectedMessage);
		//verificar que mensagem de sucesso apareceu.
		//verifica que formulario esta limpo
		
	}
	
	private TransactionViewPage goToTransactionPage() {
		TransactionViewPage p = new TransactionViewPage(webDriver, transactionViewNames);
		p.clickMenuLink("transactionMenuItem");
		return p;
	}

	public TransactionViewPage goToTransactionPageWithSampleUserLoggedIn() throws IOException, IllegalAccessException, InvocationTargetException {
		addNewUserAndLogin();
		webDriver.get(transactionViewNames.getTransactionPageTestURL());
		
		return new TransactionViewPage(webDriver, transactionViewNames);
		
	}
	
	AccountViewPage addNewUserAndLogin() throws IOException, IllegalAccessException, InvocationTargetException {
		webDriver.get(userViewNames.getUserPageTestURL());
		UserViewPage userPage = new UserViewPage(webDriver, userViewNames);
		
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(newUserPass);
		return userPage.clickButtonLoginUser();
		
	}
	
	WebDriver getWebDriver(String driverType) {
		if("firefox".equalsIgnoreCase(driverType)) {
			return new FirefoxDriver();
		}
		if("chrome".equalsIgnoreCase(driverType)) {
			return new ChromeDriver();
		}
		HtmlUnitDriver d = new HtmlUnitDriver(true);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return d;
		
	}
	
}
