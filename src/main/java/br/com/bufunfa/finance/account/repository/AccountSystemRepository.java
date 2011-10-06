package br.com.bufunfa.finance.account.repository;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import org.springframework.roo.addon.layers.repository.jpa.RooRepositoryJpa;

@RooRepositoryJpa(domainType = AccountSystem.class)
public interface AccountSystemRepository {
}
