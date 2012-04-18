package br.com.bufunfa.finance.user.service;

import org.springframework.roo.addon.layers.service.RooService;

import br.com.bufunfa.finance.user.modelo.User;

@RooService(domainTypes = { br.com.bufunfa.finance.user.modelo.User.class })
public interface UserService {
	
	public User findUserByEmailAndPassword(String email, String password);
}
