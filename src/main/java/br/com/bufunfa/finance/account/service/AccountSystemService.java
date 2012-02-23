package br.com.bufunfa.finance.account.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

@RooService(domainTypes = { 
		br.com.bufunfa.finance.account.modelo.AccountSystem.class,
		br.com.bufunfa.finance.account.modelo.Account.class
		})
public interface AccountSystemService {

	Account findIncomeAccount(AccountSystem sample);
	
	Account findOutcomeAccount(AccountSystem sample);

	Account findAssetAccount(AccountSystem sample);

	Account findLiabilityAccount(AccountSystem sample);

	List<Account> findAccountByFatherId(Long id);
	
	AccountSystem findAccountSystemByUserId(String userId);

}
