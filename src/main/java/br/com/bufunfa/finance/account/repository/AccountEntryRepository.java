package br.com.bufunfa.finance.account.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import br.com.bufunfa.finance.account.modelo.AccountEntry;

@RooJpaRepository(domainType=AccountEntry.class)
public interface AccountEntryRepository {

	List<AccountEntry> findByAccountIdAndDateBetween(Long accountId, Date begin,
			Date end);
	
	@Query(value="SELECT sum(ae.value) FROM AccountEntry ae WHERE ae.account.id IN ?1 AND ae.date <= ?2")
	BigDecimal getDeepAccountBalance(Collection<Long> accountIds, Date date);

}
