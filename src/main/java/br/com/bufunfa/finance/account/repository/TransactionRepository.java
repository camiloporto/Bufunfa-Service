package br.com.bufunfa.finance.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import br.com.bufunfa.finance.account.modelo.Transaction;

@RooJpaRepository(domainType=Transaction.class)
public interface TransactionRepository {
	
	@Query("SELECT t FROM Transaction t WHERE t.originAccountEntry.date between ?1 AND ?2")
	List<Transaction> findByDateBetween(Date begin, Date end);
	
	List<Transaction> findByAccountSystemId(Long accountSystemId);
	
	@Query(
			"SELECT t " +
			"FROM Transaction t " +
			"WHERE " +
			"(t.originAccountEntry.date between ?2 AND ?3) AND " +
			"(t.originAccountEntry.account.id = ?1 OR t.destAccountEntry.account.id = ?1)"
		)
	List<Transaction> findByAccountIdAndDateBetween(Long accountId, Date begin,
			Date end);

}
