package br.com.bufunfa.finance.account.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.TransactionViewPage.TransactionForm;
import br.com.bufunfa.finance.account.ui.jsf.config.TransactionViewNames;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.config.UserDataTestCleaner;
import br.com.bufunfa.finance.user.ui.jsf.UserViewPage;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class TransactionViewTest extends SpringRootTestsConfiguration {
	
	private static final String ASSET_ACCOUNT_NAME = "Ativos";
	private static final String LIABILITY_ACCOUNT_NAME = "Passivos";
	
	@Autowired
	private UserDataTestCleaner userDataCleaner;
	
	private UserViewNames userViewNames;
	
	private TransactionViewNames transactionViewNames;
	
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
		
		this.webDriver = getWebDriver();
	}
	
	@Before
	public void cleanUserData() {
		userDataCleaner.clearAllUsers();
	}
	
	@Test
	@Ignore
	public void testAddNewTransaction_shouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		
		TransactionViewPage page = goToTransactionPageWithSampleUserLoggedIn();
		
		final String date = "05/02/2012";
		final String value = "150";
		final String comment = TestDataGenerator.generateRandomString();
		
		TransactionForm form = page.new TransactionForm();
		form.fillFromAccount(ASSET_ACCOUNT_NAME);
		form.fillToAccount(LIABILITY_ACCOUNT_NAME);
		form.fillDate(date);
		form.fillValue(value);
		form.fillComment(comment);
		
		page.clickButtonSaveTransaction();

		page.assertThatTransactionIsPage(comment);
		//verificar que mensagem de sucesso apareceu.
		//verifica que formulario esta limpo
		
	}
	
	TransactionViewPage goToTransactionPageWithSampleUserLoggedIn() throws IOException, IllegalAccessException, InvocationTargetException {
		addNewUserAndLogin();
		webDriver.get(transactionViewNames.getTransactionPageTestURL());
		
		return new TransactionViewPage(webDriver, transactionViewNames);
		
	}
	
	void addNewUserAndLogin() throws IOException, IllegalAccessException, InvocationTargetException {
		webDriver.get(userViewNames.getUserPageTestURL());
		UserViewPage userPage = new UserViewPage(webDriver, userViewNames);
		
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(newUserPass);
		userPage.clickButtonLoginUser();
		
	}
	
	WebDriver getWebDriver() {
		HtmlUnitDriver d = new HtmlUnitDriver();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return d;
		
	}

}
