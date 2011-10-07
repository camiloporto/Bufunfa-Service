package br.com.bufunfa.finance.account.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooRepositoryJpa;

import br.com.bufunfa.finance.account.modelo.Account;

@RooRepositoryJpa(domainType=Account.class)
public interface AccountRepository {
	
	List<Account> findByFatherId(Long fatherId);

}
