package br.com.bufunfa.finance.account.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import br.com.bufunfa.finance.account.modelo.AccountSystem;

@RooJpaRepository(domainType = AccountSystem.class)
public interface AccountSystemRepository {
	
	AccountSystem findAccountSystemByUserId(String userId);
}
