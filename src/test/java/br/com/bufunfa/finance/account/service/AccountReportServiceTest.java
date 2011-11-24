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
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.ExceptionHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.service.util.TransactionHelper;

public class AccountReportServiceTest extends SpringRootTestsConfiguration {
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
	@Resource(name="accountService")
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
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_ACCOUNT.required}";

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
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_BEGIN_DATE.required}";

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
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_END_DATE.required}";

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
}
