package br.com.bufunfa.finance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.service.UserService;

@Component
public class UserController {
	
	private User user = new User();
	
	@Autowired
	private UserService userService;

	public void saveNewUser() {
		userService.saveUser(getUser());
		
	}

	public User getUser() {
		return user;
	}

}
