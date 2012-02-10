package br.com.bufunfa.finance.user.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.user.controller.util.UserControllerHelper;
import br.com.bufunfa.finance.user.ui.jsf.config.UserViewNames;
import br.com.bufunfa.finance.user.ui.jsf.util.Property2BeanUtil;

public class UserViewTest extends SpringRootTestsConfiguration {
	
	@Resource(name="userControllerHelper")
	private UserControllerHelper userControllerHelper;
	
	private UserViewNames userViewNames;
	
	public UserViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		userViewNames = new UserViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				userViewNames, 
				UserViewNames.class.getCanonicalName());
	}
	
	@Test
	public void testSaveNewUser_shouldSuccess() {
		UserViewPage userPage = getUserViewPage();
		UserViewPage.UserForm userForm = userPage.getUserForm();
		final String newUserEmail = "newuser@email.com";
		final String newUserPass = "secret";
		userForm.setEmail(newUserEmail);
		userForm.setPassword(newUserPass);
		
		userPage.clickButtonAddNewUser();
		
		userPage.assertThatAddNewUserSuccessMessageIsPresent();
		userPage.assertThatNewUserFormIsEmpty();
		
		//FIXME limpar os dados do usuario recem criado. REMOVE-lo do banco
	}
	
	@Test
	public void testLoginUser_shouldSuccess() throws IOException, IllegalAccessException, InvocationTargetException {
		final String newUserEmail = "newuser@email.com";
		final String newUserPass = "secret";
		UserViewPage userPage = getUserViewPage();
		userPage.addNewUser(newUserEmail, newUserPass);
		
		UserViewPage.UserForm loginForm = userPage.getUserForm();
		
		loginForm.setEmail(newUserEmail);
		loginForm.setPassword(newUserPass);
		
		AccountViewPage accountPage = userPage.clickButtonLoginUser();
		accountPage.assertThatIsOnThePage();
		
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
