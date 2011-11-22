package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.service.RooService;

import br.com.bufunfa.finance.account.modelo.Transaction;

@RooService(domainTypes = { br.com.bufunfa.finance.account.modelo.Transaction.class })
public interface TransactionService {

	Transaction saveNewTransaction(Long idOriginAccount, Long idDestAccount, Date date,
			BigDecimal value, String comment);

	Transaction updateTransaction(
			Long idTransaction, Long idOriginAccount, Long idDestAccount, Date date,
			BigDecimal value, String comment);

	void deleteTransaction(Long id);

	@Query("SELECT t FROM Transaction t WHERE t.originAccountEntry.date between 1? AND 2?")
	List<Transaction> findByDateBetween(Date begin, Date end);

}
