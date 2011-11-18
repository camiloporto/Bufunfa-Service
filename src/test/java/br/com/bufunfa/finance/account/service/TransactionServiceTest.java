package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.util.AccountHelper;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.ExceptionHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class TransactionServiceTest {
	
	@Resource(name="transactionService")
	private TransactionService transactionService;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="accountHelper")
    private AccountHelper accountTestHelper;
	
	@Resource(name="accountService")
	private AccountSystemService accountService;
	
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	@Test
	public void testSaveNewTransaction_shouldSuccess() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		Account dest = accountService.findOutcomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Transaction saved = transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				date, value, comment);
		
		Assert.assertNotNull("should create not null transaction", saved);
		Assert.assertNotNull("should assign a valid id to transatcion", saved.getId());
		Assert.assertNotNull("should assign a valid id to origin accountEntry", saved.getOriginAccountEntry().getId());
		Assert.assertNotNull("should assign a valid id to dest accountEntry", saved.getDestAccountEntry().getId());
	}
	
	@Test
	public void testSaveTransactionWithNullOriginAccount_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account dest = accountService.findOutcomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Long idOriginAccount = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT_ID.required}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with null accountOrigin Id", 
				idOriginAccount, 
				dest.getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNullDestAccount_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Long idDestAccount = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT_ID.required}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with null accountDest Id", 
				origin.getId(), 
				idDestAccount, 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNullValue_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		Account dest = accountService.findOutcomeAccount(sample);
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		BigDecimal value = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.VALUE.required}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with null value", 
				origin.getId(), 
				dest.getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNullDate_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		Account dest = accountService.findOutcomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		
		String comment = "first year money spent";
		
		Date date = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.DATE.required}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with null date", 
				origin.getId(), 
				dest.getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNonExistentOriginAccount_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account dest = accountService.findOutcomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Long nonExistentOriginAccountId = -1L;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT.notfound}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with nonexistent origin account", 
				nonExistentOriginAccountId, 
				dest.getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNonExistentDestAccount_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Long nonExistentDestAccountId = -1L;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT.notfound}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with nonexistent destination account", 
				origin.getId(),
				nonExistentDestAccountId,
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testSaveTransactionWithNegativeValue_shouldThrowsException() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		Account dest = accountService.findOutcomeAccount(sample);
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		BigDecimal value = new BigDecimal("-100.00");
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.VALUE.negative}";
		runTestSaveInvalidTransaction_shouldThrowsException(
				"should not save transaction with negative value", 
				origin.getId(), 
				dest.getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
	}
	
	private void runTestSaveInvalidTransaction_shouldThrowsException(
			String failMessage, 
			Long originAccountId,
			Long destAccountId,
			BigDecimal value,
			Date date,
			String comment,
			String...expectedTemplateErrorMessages) {
		try {
			transactionService.saveNewTransaction(
					originAccountId, 
					destAccountId, 
					date, value, comment);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
	}
	
	@Test
	public void testUpdateTransactionToNewOriginAccount_shouldSuccess() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		Account origin = accountService.findIncomeAccount(sample);
		Account dest = accountService.findOutcomeAccount(sample);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		Transaction saved = transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				date, value, comment);
		
		Account newAccount = accountTestHelper.createSample("Salario",
				origin.getId()).getAccount();
		accountService.saveAccount(newAccount);
		
		Transaction updated = transactionService.updateTransaction(
				saved.getId(),
				newAccount.getId(), 
				dest.getId(), 
				date, value, comment);
		
		Assert.assertEquals(
				"transaction should preserved id", 
				saved.getId(), updated.getId());
		Assert.assertNotSame(
				"transaction origin account should have changed", 
				saved.getOriginAccountEntry().getAccount().getId(), 
				updated.getOriginAccountEntry().getAccount().getId());
	}
	
	@Test
	public void testUpdateTransactionToNullOriginAccount_shouldThrowsException() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
			
		Transaction saved = saveSampleTransaction(accountSystem);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		Long newOriginAccountId = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT_ID.required}";
		
		runTestUpdateInvalidTransaction_shouldThrowsException(
				"should not update transaction with null origin account id", 
				saved.getId(), 
				newOriginAccountId, 
				saved.getDestAccountEntry().getAccount().getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
		
	}
	
	@Test
	public void testUpdateTransactionToNonexistentOriginAccount_shouldThrowsException() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
			
		Transaction saved = saveSampleTransaction(accountSystem);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		Long newOriginAccountId = -1L;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT.notfound}";
		
		runTestUpdateInvalidTransaction_shouldThrowsException(
				"should not update transaction to nonexistent origin account", 
				saved.getId(), 
				newOriginAccountId, 
				saved.getDestAccountEntry().getAccount().getId(), 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
		
	}
	
	@Test
	public void testUpdateTransactionToNullDestAccount_shouldThrowsException() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
			
		Transaction saved = saveSampleTransaction(accountSystem);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		Long newDestAccountId = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT_ID.required}";
		
		runTestUpdateInvalidTransaction_shouldThrowsException(
				"should not update transaction with null dest account id", 
				saved.getId(),
				saved.getOriginAccountEntry().getAccount().getId(),
				newDestAccountId, 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
		
	}
	
	@Test
	public void testUpdateTransactionToNonexistentDestAccount_shouldThrowsException() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
			
		Transaction saved = saveSampleTransaction(accountSystem);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		Long newDestAccountId = -1L;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT.notfound}";
		
		runTestUpdateInvalidTransaction_shouldThrowsException(
				"should not update transaction to nonexistent dest account", 
				saved.getId(),
				saved.getOriginAccountEntry().getAccount().getId(),
				newDestAccountId, 
				value, 
				date, 
				comment, 
				expectedTemplateErrorMessage);
		
	}
	
	private void runTestUpdateInvalidTransaction_shouldThrowsException(
			String failMessage,
			Long idTransaction,
			Long originAccountId,
			Long destAccountId,
			BigDecimal value,
			Date date,
			String comment,
			String...expectedTemplateErrorMessages) {
		try {
			transactionService.updateTransaction(
					idTransaction,
					originAccountId, 
					destAccountId, 
					date, value, comment);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
	}
	
	private Transaction saveSampleTransaction(AccountSystem as) {
		Account origin = accountService.findIncomeAccount(as);
		Account dest = accountService.findOutcomeAccount(as);
		BigDecimal value = new BigDecimal("100.00");
		Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		String comment = "first year money spent";
		
		return transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				date, value, comment);
	}

}
