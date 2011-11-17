package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.bufunfa.finance.account.modelo.Transaction;

@RooService(domainTypes = { br.com.bufunfa.finance.account.modelo.Transaction.class })
public interface TransactionService {

	Transaction saveNewTransaction(Long idOriginAccount, Long idDestAccount, Date date,
			BigDecimal value, String comment);
}
