package br.com.bufunfa.finance.account.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooRepositoryJpa;

import br.com.bufunfa.finance.account.modelo.Transaction;

@RooRepositoryJpa(domainType=Transaction.class)
public interface TransactionRepository {

}
