package br.com.bufunfa.finance.account.service.util;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountRepository;

public class AccountHelper {
	
	private Account account = new Account();
	
	@Autowired
	private AccountRepository accountRepository;

	public AccountHelper createSample(String name, Long fatherId) {
		account = new Account();
		account.setName(name);
		account.setFatherId(fatherId);
		
		return this;
	}

	public Account getAccount() {
		return account;
	}
	public Account findIncomeOf(AccountSystem saved) {
		
		Account a = accountRepository.findByFatherIdAndName(
				saved.getRootAccountId(), 
				Account.INCOME_NAME);
		
		return a;
	}

}
