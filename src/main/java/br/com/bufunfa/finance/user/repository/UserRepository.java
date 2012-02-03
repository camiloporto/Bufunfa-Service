package br.com.bufunfa.finance.user.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import br.com.bufunfa.finance.user.modelo.User;

@RooJpaRepository(domainType = User.class)
public interface UserRepository {
	
	User findByEmailAndPassword(String email, String password);
}
