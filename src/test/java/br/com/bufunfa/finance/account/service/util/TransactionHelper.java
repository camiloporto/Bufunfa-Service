package br.com.bufunfa.finance.account.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.TransactionService;

public class TransactionHelper {
	
	@Autowired
	private TransactionService transactionService;
	
//	@Resource(name="accountService")
	@Autowired
	private AccountSystemService accountService;
	
	private List<Transaction> inserted = new ArrayList<Transaction>();
	
	public Transaction saveSampleTransactionFromIncomeToOutcomeOnDate(
			AccountSystem as, Date transactionDate, String comment) {
		BigDecimal value = new BigDecimal("100.00");
		return saveSampleTransactionFromIncomeToOutcomeOnDate(as, transactionDate, comment, value);
		
	}

	public Transaction saveSampleTransactionFromIncomeToOutcomeOnDate(
			AccountSystem as, Date transactionDate, String comment, BigDecimal value) {
		Account origin = accountService.findIncomeAccount(as);
		Account dest = accountService.findOutcomeAccount(as);
		
		Transaction t = transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				transactionDate, value, comment);
		
		inserted.add(t);
		return t;
	}
	
	public Transaction saveSampleTransactionFromIncomeToAssetOnDate(
			AccountSystem as, Date transactionDate, String comment, BigDecimal value) {
		Account origin = accountService.findIncomeAccount(as);
		Account dest = accountService.findAssetAccount(as);
		
		Transaction t = transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				transactionDate, value, comment);
		
		inserted.add(t);
		return t;
	}
	
	public Transaction saveSampleTransactionFromLiabilityToAssetOnDate(
			AccountSystem as, Date transactionDate, String comment, BigDecimal value) {
		Account origin = accountService.findLiabilityAccount(as);
		Account dest = accountService.findAssetAccount(as);
		
		Transaction t = transactionService.saveNewTransaction(
				origin.getId(), 
				dest.getId(), 
				transactionDate, value, comment);
		
		inserted.add(t);
		return t;
	}
	
}
