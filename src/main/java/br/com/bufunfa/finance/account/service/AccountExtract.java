package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.Transaction;

public class AccountExtract {
	
	final static Comparator<AccountEntry> BY_DATE = new Comparator<AccountEntry>() {

		@Override
		public int compare(AccountEntry o1, AccountEntry o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	};
	
	private Account account;
	
	private List<Transaction> transactionList;
	
	public AccountExtract(Account account, Collection<Transaction> transactions) {
		this.transactionList = new ArrayList<Transaction>();
		this.transactionList.addAll(transactions);
		this.account = account;
	}
	
	
	private AccountEntry getAccountEntryOfExtractAccount(Transaction t) {
		AccountEntry originEntry = t.getOriginAccountEntry();
		if(this.account.getId().equals(originEntry.getAccount().getId())) {
			return originEntry;
		}
		
		AccountEntry destEntry = t.getDestAccountEntry();
		if(this.account.getId().equals(destEntry.getAccount().getId())) {
			return destEntry;
		}
		return null;
	}

	public BigDecimal getCurrentBalance() {
		BigDecimal sum = new BigDecimal("0.00");
		for (Transaction t : this.transactionList) {
			AccountEntry accountEntry = getAccountEntryOfExtractAccount(t);
			sum = sum.add(accountEntry.getValue());
		}
		return sum;
	}

	public List<Transaction> getTransactionList() {
		return this.transactionList;
	}
	
	public Account getAccount() {
		return account;
	}

}
