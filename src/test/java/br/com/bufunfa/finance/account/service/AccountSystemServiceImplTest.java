package br.com.bufunfa.finance.account.service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.ExceptionHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AccountSystemServiceImplTest {
	
	@Resource(name="accountService")
	private AccountSystemService accountService;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	@Test
	public void testCreateNewAccountSystem_shouldCreateSuccess() {
		
		AccountSystem sample = serviceTestHelper
			.createValidSample()
			.getAccountSystem();
		
		accountService.saveAccountSystem(sample);
		AccountSystem saved = accountService.findAccountSystem(sample.getId());
		
		
		
		Assert.assertNotNull("did not returned valid saved instance", saved);
		Assert.assertNotNull("did not assigned valid id", saved.getId());
		
		
		serviceTestHelper
			.verifyInitialAccountSystemState("account system initial state not satisfied", sample);
	}
	
	@Test
	public void testCreateNewAccountSystemWithNullName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample(null, "sample")
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with null name");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}
	
	@Test
	public void testCreateNewAccountSystemWithEmptyName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("", "sample")
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with empty name");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}
	
	@Test
	public void testCreateNewAccountSystemWithBlankName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("           ", "sample")
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with empty name");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}
	
	@Test
	public void testCreateNewAccountSystemWithNullUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", null)
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with null username");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}
	
	@Test
	public void testCreateNewAccountSystemWithEmptyUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", "")
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with empty username");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}
	
	@Test
	public void testCreateNewAccountSystemWithBlankUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", "         ")
			.getAccountSystem();
		
		try {
			accountService.saveAccountSystem(invalid);
			Assert.fail("should not save accountSystem with blank username");
		} catch (ConstraintViolationException e) {
			String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateMessage);
		}
	}

}
