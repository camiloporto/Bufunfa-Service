package br.com.bufunfa.finance.account.service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountHelper;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.ExceptionHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AccountSystemServiceImplTest {
	
	@Resource(name="accountService")
	private AccountSystemService accountService;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="accountHelper")
    private AccountHelper accountTestHelper;
	
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
//	@Ignore
	public void testGetIncomeAccount_shouldSuccess() {

		AccountSystem sample = serviceTestHelper.createValidSample()
				.getAccountSystem();
		accountService.saveAccountSystem(sample);

		Account income = accountService.findIncomeAccount(sample);

		Assert.assertNotNull("should not return null income account", income);
		Assert.assertEquals("income fatherId != accountSystem root account id",
				sample.getRootAccountId(), income.getFatherId().longValue());
		Assert.assertEquals("income name != expected",
				"br.com.bufunfa.finance.modelo.account.INCOME_ACCOUNT_NAME",
				income.getName());

	}

	
	@Test
	public void testCreateNewAccountSystemWithNullName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample(null, "sample")
			.getAccountSystem();
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with null name", 
				invalid, 
				expectedTemplateMessage);
	}
	
	@Test
	public void testCreateNewAccountSystemWithEmptyName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("", "sample")
			.getAccountSystem();
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with empty name", 
				invalid, 
				expectedTemplateMessage);
	}
	
	@Test
	public void testCreateNewAccountSystemWithBlankName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("           ", "sample")
			.getAccountSystem();
		
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}";
		
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with empty name", 
				invalid, 
				expectedTemplateMessage);
	}
	
	@Test
	public void testCreateNewAccountSystemWithNullUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", null)
			.getAccountSystem();
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
		
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with null username", 
				invalid, 
				expectedTemplateMessage);
	}
	
	@Test
	public void testCreateNewAccountSystemWithEmptyUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", "")
			.getAccountSystem();
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with empty username", 
				invalid, 
				expectedTemplateMessage);
	}
	
	
	@Test
	public void testCreateNewAccountSystemWithBlankUserName_shouldThrowsException() {
		AccountSystem invalid = new AccountSystemHelper()
			.createSample("AccountSystem Sample", "         ")
			.getAccountSystem();
		String expectedTemplateMessage = "{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}";
		
		runTestCreateInvalidAccountSystem_shouldThrowsException(
				"should not save accountSystem with blank username", 
				invalid, 
				expectedTemplateMessage);
		
	}
	
	private void runTestCreateInvalidAccountSystem_shouldThrowsException(
			String failMessage, AccountSystem invalidAccountSystem, String...expectedTemplateErrorMessages) {
		
		try {
			accountService.saveAccountSystem(invalidAccountSystem);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
		
	}
	
	@Test
	public void testAddAccount_shouldSuccess() {
		
		AccountSystem sample = serviceTestHelper.createValidSample()
				.getAccountSystem();

		accountService.saveAccountSystem(sample);
		AccountSystem saved = accountService.findAccountSystem(sample.getId());
		Account income = accountTestHelper.findIncomeOf(saved);
		Account newAccount = accountTestHelper.createSample("Salario",
				income.getId()).getAccount();
		
		accountService.saveAccount(newAccount);
		
		Assert.assertNotNull("did not assigned an id", newAccount.getId());
		Assert.assertEquals("fatherId not verified", income.getId(),
				newAccount.getFatherId());
	}

	
	//TODO refatorar testes: separar logica comum em metodo: parameterized test

}