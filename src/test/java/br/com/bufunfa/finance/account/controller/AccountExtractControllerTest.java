package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountExtract;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.DateUtils;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.service.util.TransactionHelper;

public class AccountExtractControllerTest extends SpringRootTestsConfiguration {
	
	private static final String ASSET_ACCOUNT_NAME = "ativos";
	
	@Autowired
	private AccountExtractController controller;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
	@Autowired
	private AccountSystemService accountService;
	
	@Test
	public void testAccountExtractWithValidValues_ShouldRetrieveAccountExtract() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2012, Calendar.JANUARY, 1).getTime();
		Date date2 = new GregorianCalendar(2012, Calendar.JANUARY, 10).getTime();
		Date date3 = new GregorianCalendar(2012, Calendar.JANUARY, 31).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account source = accountService.findIncomeAccount(sample);
		
		Date beginDate = DateUtils.getDate(2012, Calendar.JANUARY, 1).getTime();
		Date endDate = DateUtils.getDate(2012, Calendar.JANUARY, 31).getTime();
		
		controller.getAccount().setId(source.getId());
		controller.setBeginDate(beginDate);
		controller.setEndDate(endDate);
		controller.updateExtract();
		
		BigDecimal expectedCurrentBalance = new BigDecimal("-175.00");
		int expectedEntryCount = 3;
		
		verifyExtract(
				"accountExtract not expected",
				controller.getAccountExtract(),
				expectedCurrentBalance,
				expectedEntryCount,
				beginDate,
				endDate
				);
		
	}
	
	@Test
	public void testAccountExtractWithNoBeginDate_ShouldSetBeginDateToCurrentMonth1stDay() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		Account source = accountService.findIncomeAccount(sample);
		
		Date beginDate = null;
		Date endDate = DateUtils.getDate(2012, Calendar.JANUARY, 31).getTime();
		
		controller.getAccount().setId(source.getId());
		controller.setBeginDate(beginDate);
		controller.setEndDate(endDate);
		controller.updateExtract();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date expectedBeginDate = c.getTime();
		
		Date actualBeginDate = controller.getBeginDate();
		Assert.assertEquals("begin date not configured properly", expectedBeginDate, actualBeginDate);
		
		
	}
	
	@Test
	public void testAccountExtractWithNoEndDate_ShouldSetEndDateToCurrentMonthLastDay() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		
		Account source = accountService.findIncomeAccount(sample);
		
		Date beginDate = DateUtils.getDate(2012, Calendar.JANUARY, 15).getTime();;
		Date endDate = null;
		
		controller.getAccount().setId(source.getId());
		controller.setBeginDate(beginDate);
		controller.setEndDate(endDate);
		controller.updateExtract();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date expectedEndDate = c.getTime();
		
		Date actualEndDate = controller.getEndDate();
		Assert.assertEquals("end date not configured properly", expectedEndDate, actualEndDate);
		
		
	}

	private void verifyExtract(String msg, AccountExtract accountExtract,
			BigDecimal expectedCurrentBalance, int expectedEntryCount,
			Date beginDate, Date endDate) {
		Assert.assertEquals(
				msg,
				expectedCurrentBalance, 
				accountExtract.getCurrentBalance());
		
		Assert.assertEquals(
				msg,
				expectedEntryCount, 
				accountExtract.getTransactionList().size());
		
	}

}
