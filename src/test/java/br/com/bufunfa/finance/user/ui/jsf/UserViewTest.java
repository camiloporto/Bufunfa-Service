package br.com.bufunfa.finance.user.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.user.config.UserDataTestCleaner;
import br.com.bufunfa.finance.user.controller.util.UserControllerHelper;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;
import br.com.bufunfa.finance.user.ui.jsf.util.Property2BeanUtil;
import br.com.bufunfa.finance.user.ui.jsf.util.TestDataGenerator;

public class UserViewTest extends SpringRootTestsConfiguration {
	
	@Resource(name="userControllerHelper")
	private UserControllerHelper userControllerHelper;
	
	@Autowired
	private UserDataTestCleaner userDataCleaner;
	
	private UserViewNames userViewNames;
	
	public UserViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		userViewNames = new UserViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				userViewNames, 
				UserViewNames.class.getCanonicalName());
	}
	
	@Before
	public void cleanUserData() {
		userDataCleaner.clearAllUsers();
	}
	
	@Test
	public void testSaveNewUser_shouldSuccess() {
		UserViewPage userPage = getUserViewPage();
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
		UserViewPage userPage = getUserViewPage();
		
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
		UserViewPage userPage = getUserViewPage();
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
		UserViewPage userPage = getUserViewPage();
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
		UserViewPage userPage = getUserViewPage();
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(inputPass);
		
		userPage.clickButtonLoginUser();
		userPage.assertThatIsOnThePage();
		
		userPage.assertThatErrorMessagesArePresent(
				userViewNames.getMessageInvalidUserCredentials());
		
	}
	
	

	private UserViewPage getUserViewPage() {
		WebDriver driver = getWebDriver();
		driver.get(userViewNames.getUserPageTestURL());
		
		return new UserViewPage(driver, userViewNames);
	}
	
	WebDriver getWebDriver() {
		String driverType = userViewNames.getWebDriverType();
		if(driverType == null || "".equals(driverType)) {
			return new HtmlUnitDriver();
		}
		if("firefox".equalsIgnoreCase(driverType)) {
			return new FirefoxDriver();
		}
		return new HtmlUnitDriver();
	}
	

}
