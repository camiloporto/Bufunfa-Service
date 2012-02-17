package br.com.bufunfa.finance.user.service;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.ExceptionHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.user.config.UserDataTestCleaner;
import br.com.bufunfa.finance.user.modelo.User;

public class UserServiceImplTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDataTestCleaner dataCleaner;
	
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	public UserServiceImplTest() {
		
	}
	
	@Before
	public void beforeTests() {
		dataCleaner.clearAllUsers();
	}
	
	@Test
	public void testSaveNewUser_shouldSuccess() {
		User u = new User();
		u.setEmail("newuser@email.com");
		u.setPassword("secret");
		
		userService.saveUser(u);
		
		User saved = userService.findUser(u.getId());
		
		Assert.assertNotNull("did not save the new user", saved);
		Assert.assertNotNull("did not assigned valid id", saved.getId());
	}
	
	@Test
	public void testSaveNewUserWithNullEmailInformed_shouldThrowsException() {
		User u = new User();
		u.setPassword("secret");
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.user.service.UserService.EMAIL.required}";
		//no emailinformed
		runTestSaveInvalidUser_shouldThrowsException(
				"should not save new user with no email informed", 
				u, 
				expectedTemplateMessage);
		
	}
	
	@Test
	public void testSaveNewUserWithEmptyEmailInformed_shouldThrowsException() {
		User u = new User();
		u.setPassword("secret");
		u.setEmail("");
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.user.service.UserService.EMAIL.required}";
		//no emailinformed
		runTestSaveInvalidUser_shouldThrowsException(
				"should not save new user with no email informed", 
				u, 
				expectedTemplateMessage);
		
	}
	
	@Test
	public void testSaveNewUserWithExistentEmailInformed_shouldThrowsException() {
		User u = new User();
		u.setEmail("newuser@email.com");
		u.setPassword("secret");
		
		userService.saveUser(u);
		
		User u2 = new User();
		u2.setPassword("other-secret");
		u2.setEmail("newuser@email.com");
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.user.service.UserService.EMAIL.unique}";
		//email already exists
		runTestSaveInvalidUser_shouldThrowsException(
				"should not save new user with existent email", 
				u2, 
				expectedTemplateMessage);
		
	}
	
	@Test
	public void testSaveNewUserWithNullPasswordInformed_shouldThrowsException() {
		User u = new User();
		u.setEmail("newuser@email.com");
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.user.service.UserService.PASSWORD.required}";
		//no pass informed
		runTestSaveInvalidUser_shouldThrowsException(
				"should not save new user with no pass informed", 
				u, 
				expectedTemplateMessage);
		
	}
	
	@Test
	public void testSaveNewUserWithEmptyPasswordInformed_shouldThrowsException() {
		User u = new User();
		u.setEmail("newuser@email.com");
		u.setPassword("");
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.user.service.UserService.PASSWORD.required}";
		//no pass informed
		runTestSaveInvalidUser_shouldThrowsException(
				"should not save new user with no pass informed", 
				u, 
				expectedTemplateMessage);
		
	}
	
	private void runTestSaveInvalidUser_shouldThrowsException(
			String failMessage, User invalidUser, String...expectedTemplateErrorMessages) {
		
		try {
			userService.saveUser(invalidUser);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIsPresentAndI18nized(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
		
	}

}
