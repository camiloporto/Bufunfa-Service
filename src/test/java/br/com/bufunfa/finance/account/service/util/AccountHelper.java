package br.com.bufunfa.finance.account.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.service.AccountSystemService;

public class AccountHelper {
	
	private Account account = new Account();
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	private List<Account> accountPersisted = new ArrayList<Account>();

	public AccountHelper createSample(String name, Long fatherId) {
		account = new Account();
		account.setName(name);
		account.setFatherId(fatherId);
		
		return this;
	}
	
	public Account saveAccountSample(String name, Long fatherId) {
		Account toSave = createSample(name, fatherId).getAccount();
		accountSystemService.saveAccount(toSave);
		accountPersisted.add(toSave);
		
		return toSave;
	}

	public Account getAccount() {
		return account;
	}

}
