package br.com.bufunfa.finance.account.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJpaRepository(domainType=Account.class)
public interface AccountRepository {
	
	List<Account> findByFatherId(Long fatherId);
	
	Account findByFatherIdAndName(Long fatherId, String name);

}
