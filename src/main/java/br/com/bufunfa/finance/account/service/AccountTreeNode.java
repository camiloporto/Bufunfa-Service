package br.com.bufunfa.finance.account.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.serializable.RooSerializable;

import br.com.bufunfa.finance.account.modelo.Account;

@Configurable
@RooSerializable
public class AccountTreeNode implements Comparable<AccountTreeNode> {
	
	@Autowired
	private AccountSystemService accountSystemService;

	private Account account;
	private Account parentAccount;
	private SortedSet<AccountTreeNode> children = new TreeSet<AccountTreeNode>();
	
	public AccountTreeNode(Account parent, Account account) {
		this.account = account;
		this.parentAccount = parent;
	}
	
	
	
	public Account getAccount() {
		return account;
	}


	public Set<AccountTreeNode> getChildren() {
		List<Account> accounts = accountSystemService.findAccountByFatherId(getAccount().getId());
		for (Account account : accounts) {
			children.add(new AccountTreeNode(getAccount(), account));
		}
		return children;
	}



	@Override
	public int compareTo(AccountTreeNode o) {
		return getAccount().getName().compareTo(o.getAccount().getName());
	}

}
