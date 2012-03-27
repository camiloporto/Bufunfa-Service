package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.Transaction;

@RooSerializable
@RooJavaBean
@RooToString(excludeFields={"toAccount", "fromAccount"})
public class TransactionUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9214224711437727058L;

	
	private Account toAccount = new Account();
	private Account fromAccount = new Account();
	private BigDecimal value;
	private Date date;
	private String comment;
	private Long id;
	
	private boolean editMode = false;
	
	public TransactionUI() {
	}
	
	public TransactionUI(Transaction transaction) {
		if(transaction != null && transaction.getId() != null) {
			toAccount = transaction.getOriginAccountEntry().getAccount();
			fromAccount = transaction.getDestAccountEntry().getAccount();
			value = transaction.getDestAccountEntry().getValue();
			date = transaction.getDestAccountEntry().getDate();
			comment = transaction.getDestAccountEntry().getComment();
			id = transaction.getId();
		}
	}
}
