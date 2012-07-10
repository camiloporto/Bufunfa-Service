package br.com.bufunfa.finance.account.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.user.ui.jsf.UserViewPage;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class AccountViewTest extends SpringRootTestsConfiguration {

	private UserViewNames userViewNames;
	private AccountViewNames accountViewNames;
	private AccountViewPage viewPage;
	
	public AccountViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		accountViewNames = new AccountViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				accountViewNames, 
				AccountViewNames.class.getCanonicalName());
	}
	

	@Test
	public void testAddNewAccountToRootAccount() throws IOException, IllegalAccessException, InvocationTargetException {
		viewPage = gotoAccountViewPage();
		viewPage.addNewAccount("Ativos", "Ativos Child", "Ativos child account");
		
		String expectedInfoMessage = "Conta adicionada com sucesso";
		viewPage.wait(1);
		viewPage.assertThatInfoMessageIsPresent(
				"expectedMessage " + expectedInfoMessage + " not present", 
				expectedInfoMessage);
	}
	
	private AccountViewPage gotoAccountViewPage() throws IOException, IllegalAccessException, InvocationTargetException {
		UserViewPage loginPage = getUserViewPage();
		loginPage.addNewUser("ca", "ca");
		loginPage.getUserForm().setEmail("ca");
		loginPage.getUserForm().setPassword("ca");
		return loginPage.clickButtonLoginUser();
		
	}
	
	private UserViewPage getUserViewPage() throws IOException, IllegalAccessException, InvocationTargetException {
		userViewNames = new UserViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				userViewNames, 
				UserViewNames.class.getCanonicalName());
		WebDriver driver = getWebDriver("firefox");
		driver.get(userViewNames.getUserPageTestURL());
		
		return new UserViewPage(driver, userViewNames);
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
