package br.com.bufunfa.finance.account.repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountExtract;
import br.com.bufunfa.finance.account.service.AccountReportService;
import br.com.bufunfa.finance.account.service.AccountReportServiceImpl;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.service.util.TransactionHelper;

public class AccountEntryRepositoryTest extends SpringRootTestsConfiguration {
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
//	@Resource(name="accountService")
	@Autowired
	private AccountSystemService accountService;
	
	@Autowired
	private AccountEntryRepository accountEntryRepository;
	
	@Test
	public void testFindByDateBetween_shouldFindAccountEntriesBetweenGivenDates() {
		//precondicoes: accountsystem + conjunto de transacoes efetivadas
		AccountSystem sample = serviceTestHelper.createAndSaveAccountSystemSample();
		String comment1 = "t1";
		String comment2 = "t2";
		String comment3 = "t3";
		Date date1 = new GregorianCalendar(2011, Calendar.FEBRUARY, 1).getTime();
		Date date2 = new GregorianCalendar(2011, Calendar.FEBRUARY, 2).getTime();
		Date date3 = new GregorianCalendar(2011, Calendar.FEBRUARY, 3).getTime();
		
		BigDecimal value1 = new BigDecimal("50.00");
		BigDecimal value2 = new BigDecimal("25.00");
		BigDecimal value3 = new BigDecimal("100.00");
		
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date1, comment1, value1);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date2, comment2, value2);
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(sample, date3, comment3, value3);
		
		Account income = accountService.findIncomeAccount(sample);
		 
		List<AccountEntry> entries = accountEntryRepository.findByAccountIdAndDateBetween(
				income.getId(),
				date1,
				date2
				);
		
		// verificar se vieram os lancamentos corretos
		
		String expectedFirstEntryComment = comment1;
		String expectedSecondEntryComment = comment2;
		int expectedEntryCount = 2;
		
		Iterator<AccountEntry> it = entries.iterator();
		AccountEntry firstEntry = it.next();
		AccountEntry secondEntry = it.next();
		
		Assert.assertEquals(
				"expected entry count did not match", 
				expectedEntryCount, entries.size());
		
		Assert.assertEquals(
				"first entry did not match ", 
				expectedFirstEntryComment, firstEntry.getComment());
		
		Assert.assertEquals(
				"second entry did not match ", 
				expectedSecondEntryComment, secondEntry.getComment());
	}

}
