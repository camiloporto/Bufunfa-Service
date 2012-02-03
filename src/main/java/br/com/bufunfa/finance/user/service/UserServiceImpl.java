package br.com.bufunfa.finance.user.service;

import br.com.bufunfa.finance.user.modelo.User;

public class UserServiceImpl implements UserService {

	
	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
}
