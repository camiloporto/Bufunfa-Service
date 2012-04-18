package br.com.bufunfa.finance.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.validation.ServiceValidator;
import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.service.validation.UserConstraintGroups;
import br.com.bufunfa.finance.user.service.validation.UserParameters;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private AccountSystemService accountSystemService;

	
	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	public void saveUser(User u) {
		validateSaveNewUser(createUserParameters(u));
		userRepository.save(u);
		
		AccountSystem accountSystem = new AccountSystem();
		accountSystem.setUserId(u.getEmail());
		accountSystem.setName(u.getEmail());
		accountSystemService.saveAccountSystem(accountSystem);
	}
	
	private UserParameters createUserParameters(User u) {
		UserParameters up = new UserParameters();
		up.setEmail(u.getEmail());
		up.setPassword(u.getPassword());
		return up;
	}
	
	private void validateSaveNewUser(UserParameters params) {
		new ServiceValidator().validate(params, UserConstraintGroups.UserServiceRules.class);
	}
	
}
