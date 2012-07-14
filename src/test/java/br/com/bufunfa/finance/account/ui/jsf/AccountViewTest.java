package br.com.bufunfa.finance.account.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import javax.swing.text.View;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.user.ui.jsf.UserViewPage;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;
import br.com.bufunfa.finance.util.TestUtils;

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
	
	@After
	public void afterMethod() {
		viewPage.getDriver().close();
	}
	

	@Test
	public void testAddNewAccountToRootAccount() throws IOException, IllegalAccessException, InvocationTargetException {
		viewPage = gotoAccountViewPage();
		String accountName = TestUtils.generateRandomString(10);
		viewPage.addNewAccount("Ativos", accountName, "Ativos child account");
		
		String expectedInfoMessage = "Conta adicionada com sucesso";
		viewPage.wait(1);
		viewPage.assertThatInfoMessageIsPresent(
				"expectedMessage " + expectedInfoMessage + " not present", 
				expectedInfoMessage);
	}
	
	@Test
	public void testUpdateExistentAccount_ShouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		viewPage = gotoAccountViewPage();
		String accountName = TestUtils.generateRandomString(10);
		viewPage.addNewAccount("Ativos", accountName, "Ativos child account");
		viewPage.wait(1);
		
		WebElement ativosDiv = viewPage.getDivOfAccount("Ativos");
		viewPage.expandAccountNode(ativosDiv);
		
		WebElement accountDiv = viewPage.getDivOfAccount(accountName);
		Actions builder = new Actions(viewPage.getDriver());
		builder.moveToElement(accountDiv).build().perform();
		viewPage.wait(1);
		
		viewPage.getActionLinkOfAccountByName(accountDiv, "editar").click();
		viewPage.wait(1);
		
		String newAccountName = TestUtils.generateRandomString(9);
		viewPage.getAccountDialogPanel().fillAndSubmitDialogForm(newAccountName, "new description");
		viewPage.wait(1);
		
		String expectedInfoMessage = "Conta atualizada com sucesso";
		
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
