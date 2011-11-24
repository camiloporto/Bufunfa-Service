package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import br.com.bufunfa.finance.account.modelo.AccountEntry;

public class AccountExtract {
	
	final static Comparator<AccountEntry> BY_DATE = new Comparator<AccountEntry>() {

		@Override
		public int compare(AccountEntry o1, AccountEntry o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	};
	
	private List<AccountEntry> entries;
	
	public AccountExtract() {
		entries = new ArrayList<AccountEntry>();
	}
	
	public AccountExtract(Collection<AccountEntry> entries) {
		this.entries = new ArrayList<AccountEntry>();
		this.entries.addAll(entries);
	}

	public BigDecimal getCurrentBalance() {
		BigDecimal sum = new BigDecimal("0.00");
		for (AccountEntry ae : this.entries) {
			sum = sum.add(ae.getValue());
		}
		return sum;
	}

	public List<AccountEntry> getAccountEntries() {
		return entries;
	}

}
