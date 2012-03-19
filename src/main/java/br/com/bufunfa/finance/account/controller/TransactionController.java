package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.TransactionService;

@Controller
@RooSerializable
@RooJavaBean
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	private Account toAccount = new Account();
	private Account fromAccount = new Account();
	private BigDecimal value;
	private Date date;
	private String comment;
	
	
	
	public void saveTransaction() {
		Transaction saved = transactionService.saveNewTransaction(
				fromAccount.getId(),
				toAccount.getId(), 
				date, 
				value, 
				comment);
	}



	public List<Transaction> getAllTransaction() {
		return transactionService.findAllTransactions();
	}

}
