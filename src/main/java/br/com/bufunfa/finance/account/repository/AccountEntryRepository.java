package br.com.bufunfa.finance.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooRepositoryJpa;

import br.com.bufunfa.finance.account.modelo.AccountEntry;

@RooRepositoryJpa(domainType=AccountEntry.class)
public interface AccountEntryRepository {

	List<AccountEntry> findByAccountIdAndDateBetween(Long accountId, Date begin,
			Date end);

}
