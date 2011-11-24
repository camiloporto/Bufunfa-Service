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
	public void testGetAccountExtractWithNullAccount_shouldThrowsException() {

		Date date1 = new GregorianCalendar(2011, Calendar.MARCH, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();

		Account nullAccount = null;
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_ACCOUNT.required}";

		try {
			reportService.getAccountExtract(nullAccount, date1, date2);
			Assert.fail("should throws expected exception: null account given");
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", e,
					expectedTemplateErrorMessage);
		}
	}
	//FIXME adicionar teste quando a conta nao existe. deve vir extrato vazio. isso tb serve para ac.id = null;
	
	@Test
	public void testGetAccountExtractWithNullBeginDate_shouldThrowsException() {

		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		Date nullDate = null;
		Date endDate = new GregorianCalendar(2011, Calendar.MARCH, 2).getTime();

		Account account = accountService.findIncomeAccount(sample);
		String expectedTemplateErrorMessage = "{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_BEGIN_DATE.required}";

		try {
			reportService.getAccountExtract(account, nullDate, endDate);
			Assert.fail("should throws expected exception: begin date is null");
		} catch (ConstraintViolationException e) {
			exceptionHelper.verifyTemplateErrorMessagesIn(
					"did not throws the correct template error message", e,
					expectedTemplateErrorMessage);
		}
	}
}
