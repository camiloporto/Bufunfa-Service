package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountHelper;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.ExceptionHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.service.util.TransactionHelper;

public class AccountReportServiceTest extends SpringRootTestsConfiguration {
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
	@Resource(name="accountHelper")
    private AccountHelper accountTestHelper;
	
	@Autowired
	private AccountSystemService accountService;
	
	@Autowired
	private AccountReportService reportService;
	
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	@Test
	public void testGetAccountExtract_shouldSuccess() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account income = accountService.findIncomeAccount(sample);
		AccountExtract extract = reportService.getAccountExtract(
				income,
				date1,
				date2
				);
		
		
		BigDecimal expectedCurrentBalance = new BigDecimal("-75.00");
		String expectedFirstEntryComment = comment1;
		String expectedSecondEntryComment = comment2;
		int expectedEntryCount = 2;
		
		BigDecimal currentBalance = extract.getCurrentBalance();
		List<AccountEntry> entries = extract.getAccountEntries();
		Iterator<AccountEntry> it = entries.iterator();
		AccountEntry firstEntry = it.next();
		AccountEntry secondEntry = it.next();
		
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"current balance dit not match", 
				expectedCurrentBalance, currentBalance);
		Assert.assertEquals(
				"first entry did not match ", 
				expectedFirstEntryComment, firstEntry.getComment());
		
		Assert.assertEquals(
				"second entry did not match ", 
				expectedSecondEntryComment, secondEntry.getComment());
	}
	
	@Test
	public void testGetEmptyAccountExtract_shouldSuccess() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		
		Account income = accountService.findIncomeAccount(sample);
		AccountExtract extract = reportService.getAccountExtract(
				income,
				date1,
				date2
				);
		
		
		BigDecimal expectedCurrentBalance = new BigDecimal("0.00");
		int expectedEntryCount = 0;
		
		BigDecimal currentBalance = extract.getCurrentBalance();
		List<AccountEntry> entries = extract.getAccountEntries();
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"current balance dit not match", 
				expectedCurrentBalance, currentBalance);
	}
	
	@Test
	public void testGetAccountExtractWithBeginDateGreateThanEndDate_shouldRetrieveEmptyExtract() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account income = accountService.findIncomeAccount(sample);
		AccountExtract extract = reportService.getAccountExtract(
				income,
				date2,
				date1
				);
		
		
		BigDecimal expectedCurrentBalance = new BigDecimal("0.00");
		int expectedEntryCount = 0;
		
		BigDecimal currentBalance = extract.getCurrentBalance();
		List<AccountEntry> entries = extract.getAccountEntries();
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"current balance dit not match", 
				expectedCurrentBalance, currentBalance);
	}
	
	@Test
	public void testGetAccountExtractWithNonexistentAccount_shouldRetrieveEmptyExtract() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account nonExistentAccount = new Account();
		nonExistentAccount.setId(99999L);
		AccountExtract extract = reportService.getAccountExtract(
				nonExistentAccount,
				date2,
				date1
				);
		
		
		BigDecimal expectedCurrentBalance = new BigDecimal("0.00");
		int expectedEntryCount = 0;
		
		BigDecimal currentBalance = extract.getCurrentBalance();
		List<AccountEntry> entries = extract.getAccountEntries();
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"current balance dit not match", 
				expectedCurrentBalance, currentBalance);
	}
	
	@Test
	public void testGetAccountExtractWithNullAccountId_shouldRetrieveEmptyExtract() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account transientAccount = new Account();
		transientAccount.setId(null);
		AccountExtract extract = reportService.getAccountExtract(
				transientAccount,
				date2,
				date1
				);
		
		
		BigDecimal expectedCurrentBalance = new BigDecimal("0.00");
		int expectedEntryCount = 0;
		
		BigDecimal currentBalance = extract.getCurrentBalance();
		List<AccountEntry> entries = extract.getAccountEntries();
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"current balance dit not match", 
				expectedCurrentBalance, currentBalance);
	}
	
	@Test
	public void testGetAccountExtractWithNullAccount_shouldThrowsException() {

		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();

		Account nullAccount = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT.required}";

		runTestGetInvalidAccountExtract_shouldThrowsException(
				"should throws expected exception: null account given", 
				nullAccount, 
				date1, 
				date2, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testGetAccountExtractWithNullBeginDate_shouldThrowsException() {

		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		Date nullDate = null;
		Date endDate = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();

		Account account = accountService.findIncomeAccount(sample);
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.BEGIN_DATE.required}";

		runTestGetInvalidAccountExtract_shouldThrowsException(
				"should throws expected exception: begin date is null", 
				account, 
				nullDate, 
				endDate, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testGetAccountExtractWithNullEndDate_shouldThrowsException() {

		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		
		Date beginDate = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date nullDate = null;

		Account account = accountService.findIncomeAccount(sample);
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.END_DATE.required}";

		runTestGetInvalidAccountExtract_shouldThrowsException(
				"should throws expected exception: end date is null", 
				account, 
				beginDate, 
				nullDate, 
				expectedTemplateErrorMessage);
	}
	
	private void runTestGetInvalidAccountExtract_shouldThrowsException(
			String failMessage, Account account, Date beginDate, Date endDate, String...expectedTemplateErrorMessages) {
		
		try {
			reportService.getAccountExtract(account, beginDate, endDate);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
	}
	
	@Test
	public void testGetAccountBalance_shouldSumAccountEntriesInGivenTimePeriod() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account account = accountService.findIncomeAccount(sample);
		BigDecimal accountBalance = reportService.getAccountBalance(account, date3);
		
		BigDecimal expectedBalance = new BigDecimal("-175.00");
		
		Assert.assertEquals(
				"current account balance dit not match", 
				expectedBalance, accountBalance);
	}
	
	@Test
	public void testGetAccountBalanceWithChildren_shouldSumAccountEntriesIncludingChildren() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
		Account income = accountService.findIncomeAccount(accountSystem);
		Account outcome = accountService.findOutcomeAccount(accountSystem);
		Account salary = accountTestHelper.saveAccountSample("Salary", income.getId());
		Account rent = accountTestHelper.saveAccountSample("Rent", salary.getId());
		
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransaction(salary, outcome, date1, value1);
		transactionHelper.saveSampleTransaction(rent, outcome, date2, value2);
		transactionHelper.saveSampleTransaction(rent, outcome, date3, value3);
		
		Account account = accountService.findIncomeAccount(accountSystem);
		BigDecimal accountBalance = reportService.getAccountBalance(account, date3);
		
		BigDecimal expectedBalance = new BigDecimal("-175.00");
		
		Assert.assertEquals(
				"current account balance dit not match", 
				expectedBalance, accountBalance);
	}
	
	@Test
	public void testGetAccountBalanceWithNoEntries_shouldReturnZero() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
		Account income = accountService.findIncomeAccount(accountSystem);
		Account outcome = accountService.findOutcomeAccount(accountSystem);
		Account salary = accountTestHelper.saveAccountSample("Salary", income.getId());
		Account rent = accountTestHelper.saveAccountSample("Rent", salary.getId());
		
		Date date = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		
		Account account = accountService.findIncomeAccount(accountSystem);
		BigDecimal accountBalance = reportService.getAccountBalance(account, date);
		
		BigDecimal expectedBalance = new BigDecimal("0.00");
		
		Assert.assertEquals(
				"current account balance dit not match", 
				expectedBalance, accountBalance);
	}
	
	@Test
	public void testGetAccountBalanceWithNullAccount_shouldThrowsException() {
		Date date = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		Account account = null;
		
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT.required}";
		runTestGetInvalidAccountBalance_shouldThrowsException(
				"did not throws expected exception: null account informed to account balance", 
				account, 
				date, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testGetAccountBalanceWithTransientAccount_shouldThrowsException() {
		Date date = new GregorianCalendar(2011, Calendar.MARCH, 3).getTime();
		Account account = new Account();
		
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT.required}";
		runTestGetInvalidAccountBalance_shouldThrowsException(
				"did not throws expected exception: account informed do not exist in database", 
				account, 
				date, 
				expectedTemplateErrorMessage);
	}
	
	@Test
	public void testGetAccountBalanceWithNullDate_shouldThrowsException() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
		Account account = accountService.findIncomeAccount(accountSystem);
		Date date = null;
		
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.END_DATE.required}";
		runTestGetInvalidAccountBalance_shouldThrowsException(
				"did not throws expected exception: null date informed to account balance", 
				account, 
				date, 
				expectedTemplateErrorMessage);
	}
	
	private void runTestGetInvalidAccountBalance_shouldThrowsException(
			String failMessage, Account account, Date date, String...expectedTemplateErrorMessages) {
		
		try {
			reportService.getAccountBalance(account, date);
			Assert.fail(failMessage);
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", 
					e, 
					expectedTemplateErrorMessages);
		}
	}
	
	@Test
	public void testGetBalanceSheet_shouldSuccess() {
		AccountSystem accountSystem = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToAssetOnDate(accountSystem, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromLiabilityToAssetOnDate(accountSystem, date2, comment2, value2);
		
		BalanceSheet balance = reportService.getBalanceSheet(
				accountSystem,
				date2
				);
		
		
		BigDecimal expectedAssetBalance = new BigDecimal("75.00");
		BigDecimal expectedLiabilityBalance = new BigDecimal("-25.00");
		BigDecimal currentAssetBalance = balance.getAssetBalance();
		BigDecimal currentLiabilityBalance = balance.getLiabilityBalance();
		
		
		Assert.assertEquals(
				"current asset balance dit not match", 
				expectedAssetBalance, currentAssetBalance);
		
		Assert.assertEquals(
				"current liability balance dit not match", 
				expectedLiabilityBalance, currentLiabilityBalance);
	}
	
}
