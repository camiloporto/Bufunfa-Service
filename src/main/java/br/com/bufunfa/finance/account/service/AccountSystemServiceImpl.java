package br.com.bufunfa.finance.account.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountRepository;


public class AccountSystemServiceImpl implements AccountSystemService {
	
//	@Autowired
//	private AccountRepository accountRepository;
	
//	public void setAccountRepository(AccountRepository accountRepository) {
//		this.accountRepository = accountRepository;
//	}
	
	public void saveAccountSystem(AccountSystem accountSystem) {
		accountSystemRepository.save(accountSystem);
		createInitialAccountHierarchy(accountSystem);
	}
	
	private void createInitialAccountHierarchy(AccountSystem as) {
		Account root = new Account();
		root.setName(as.getName());
		accountRepository.save(root);
		
		Long rootId = root.getId();
		as.setRootAccountId(rootId);
		
		Account income = createAccount(
				"br.com.bufunfa.finance.modelo.account.INCOME_ACCOUNT_NAME", 
				rootId);
		
		Account outcome = createAccount(
				"br.com.bufunfa.finance.modelo.account.OUTCOME_ACCOUNT_NAME", 
				rootId);
		
		Account asset = createAccount(
				"br.com.bufunfa.finance.modelo.account.ASSET_ACCOUNT_NAME", 
				rootId);
		
		Account liability = createAccount(
				"br.com.bufunfa.finance.modelo.account.LIABILITY_ACCOUNT_NAME", 
				rootId);
		
		accountRepository.save(Arrays.asList(income, outcome, asset, liability));
	}
	
	private Account createAccount(String name, Long fatherId) {
		Account a = new Account();
		a.setName(name);
		a.setFatherId(fatherId);
		
		return a;
	}
}
