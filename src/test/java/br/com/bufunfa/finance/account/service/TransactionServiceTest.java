package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class TransactionServiceTest {
	
	@Resource(name="transactionService")
	private TransactionService transactionService;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Resource(name="accountService")
	private AccountSystemService accountService;
	
	@Test
	public void testSaveNewTransaction_shouldSuccess() {
		AccountSystem sample = serviceTestHelper.createValidSample()
				.getAccountSystem();
		accountService.saveAccountSystem(sample);

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
		//TODO verificar valores da transacao (contas, etc..) 
	}

}
