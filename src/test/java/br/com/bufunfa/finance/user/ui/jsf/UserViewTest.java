package br.com.bufunfa.finance.user.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.AccountViewPage;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.config.UserDataTestCleaner;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;

public class UserViewTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private UserDataTestCleaner userDataCleaner;
	
	private UserViewNames userViewNames;
	UserViewPage userPage;
	
	public UserViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		userViewNames = new UserViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				userViewNames, 
				UserViewNames.class.getCanonicalName());
	}
	
	@Before
	public void cleanUserData() {
		userDataCleaner.clearAllUsers();
		userPage = getUserViewPage();
	}
	
	@After
	public void afterTest() {
		userPage.getDriver().close();
	}
	
	@Test
	public void testSaveNewUser_shouldSuccess() {
		UserViewPage.UserForm userForm = userPage.getUserForm();
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userForm.setEmail(newUserEmail);
		userForm.setPassword(newUserPass);
		
		userPage.clickButtonAddNewUser();
		
		userPage.assertThatAddNewUserSuccessMessageIsPresent();
		userPage.assertThatNewUserFormIsEmpty();
		
	}
	@Test
	public void testSaveNewUserRequiredFields_shouldShowErrorMessages() {
		
		//click save user without filling required fields
		userPage.clickButtonAddNewUser();
		
		userPage.assertThatErrorMessagesArePresent(
				userViewNames.getMessageValidEmailRequired(),
				userViewNames.getMessagePasswordRequired());
		
	}
	@Test
	public void testSaveNewUserWithExistentEmail_shouldShowErrorMessages() {
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm userForm = userPage.getUserForm();
		userForm.setEmail(newUserEmail);//email already exists
		userForm.setPassword("newsecret");
		
		userPage.clickButtonAddNewUser();
		
		userPage.assertThatErrorMessagesArePresent(
				userViewNames.getMessageEmailAlreadyExists());
		
	}
	@Test
	public void testLoginUser_shouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(newUserPass);
		
		AccountViewPage accountPage = userPage.clickButtonLoginUser();
		accountPage.assertThatIsOnThePage();
		accountPage.assertThatUserInfoIsOnThePage(newUserEmail);
		
	}
	
	@Test
	public void testFailLoginUser_shouldFail() throws IOException, IllegalAccessException, InvocationTargetException {
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		final String inputPass = "wrongpass";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(inputPass);
		
		userPage.clickButtonLoginUser();
		userPage.assertThatIsOnThePage();
		
		userPage.assertThatErrorMessagesArePresent(
				userViewNames.getMessageInvalidUserCredentials());
		
	}
	@Test
	public void testLogoutUser_shouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		final String newUserEmail = TestDataGenerator.generateValidEmail();
		final String newUserPass = "secret";
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(newUserPass);
		AccountViewPage accountPage = userPage.clickButtonLoginUser();
		
		accountPage.assertThatIsOnThePage();
		accountPage.assertThatUserInfoIsOnThePage(newUserEmail);
		accountPage.clickLinkLogoutUser();
		
		userPage.assertThatIsOnThePage();
		
	}
	
	

	private UserViewPage getUserViewPage() {
		WebDriver driver = getWebDriver();
		driver.get(userViewNames.getUserPageTestURL());
		
		return new UserViewPage(driver, userViewNames);
	}
	
	WebDriver getWebDriver() {
		String driverType = userViewNames.getWebDriverType();
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
	
	WebDriver createChromeWebDriver() {
		return new ChromeDriver();
	}
	

}
