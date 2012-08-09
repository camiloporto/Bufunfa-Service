package br.com.bufunfa.finance.account.repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.service.util.TransactionHelper;

public class TransactionRepositoryTest extends SpringRootTestsConfiguration {
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
	@Autowired
	private AccountSystemService accountService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Test
	public void testFindByAccountAndDateBetween_shouldFindAccountTransactionsBetweenGivenDates() {
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();

		Date date1 = new GregorianCalendar(2011, Calendar.FEBRUARY, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.FEBRUARY, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.FEBRUARY, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		Account income = accountService.findIncomeAccount(sample);
		Account outcome = accountService.findOutcomeAccount(sample);
		
		transactionHelper.saveSampleTransaction(sample, income, outcome, date1, value1);
		transactionHelper.saveSampleTransaction(sample, outcome, income, date2, value2);
		transactionHelper.saveSampleTransaction(sample, income, outcome, date3, value3);
		 
		List<Transaction> transactions = transactionRepository.findByAccountIdAndDateBetween(
				income.getId(),
				date1,
				date2
				);
		
		int expectedEntryCount = 2;
		
		Iterator<Transaction> it = transactions.iterator();
		Transaction firstTransaction = it.next();
		Transaction secondTransaction = it.next();
		Long expectedFirstTransactionOriginAccountId = income.getId();
		Long expectedSecondTransactionOriginAccountId = outcome.getId();
		
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, transactions.size());
		
		Assert.assertEquals(
				"first transaction origin account did not match ", 
				expectedFirstTransactionOriginAccountId, 
				firstTransaction.getOriginAccountEntry().getAccount().getId());
		
		Assert.assertEquals(
				"second transaction origin account did not match ", 
				expectedSecondTransactionOriginAccountId, 
				secondTransaction.getOriginAccountEntry().getAccount().getId());
		
		
	}

}
