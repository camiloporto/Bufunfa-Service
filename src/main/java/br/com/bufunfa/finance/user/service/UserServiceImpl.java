package br.com.bufunfa.finance.user.service;

import br.com.bufunfa.finance.account.service.validation.ServiceValidator;
import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.service.validation.UserConstraintGroups;
import br.com.bufunfa.finance.user.service.validation.UserParameters;

public class UserServiceImpl implements UserService {

	
	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	public void saveUser(User u) {
		validateSaveNewUser(createUserParameters(u));
		userRepository.save(u);
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
