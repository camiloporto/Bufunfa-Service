package br.com.bufunfa.finance.account.service;

import java.util.Arrays;
import java.util.List;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;


public class AccountSystemServiceImpl implements AccountSystemService {
	
	public void saveAccountSystem(AccountSystem accountSystem) {
		accountSystemRepository.save(accountSystem);
		createInitialAccountHierarchy(accountSystem);
	}
	
	public List<Account> findAccountByFatherId(Long fatherId) {
		return accountRepository.findByFatherId(fatherId);
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
	
	public Account findIncomeAccount(AccountSystem accountSystem) {
		Account a = accountRepository.findByFatherIdAndName(
				accountSystem.getRootAccountId(), Account.INCOME_NAME);

		return a;
	}
	
	public Account findOutcomeAccount(AccountSystem accountSystem) {
		Account a = accountRepository.findByFatherIdAndName(
				accountSystem.getRootAccountId(), Account.OUTCOME_NAME);

		return a;
	}
	
	public Account findAssetAccount(AccountSystem accountSystem) {
		Account a = accountRepository.findByFatherIdAndName(
				accountSystem.getRootAccountId(), Account.ASSET_NAME);

		return a;
	}
	
	public Account findLiabilityAccount(AccountSystem accountSystem) {
		Account a = accountRepository.findByFatherIdAndName(
				accountSystem.getRootAccountId(), Account.LIABILITY_NAME);

		return a;
	}
}
