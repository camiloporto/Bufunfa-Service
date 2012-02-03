package br.com.bufunfa.finance.user.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.bufunfa.finance.user.controller.UserController;
import br.com.bufunfa.finance.user.service.UserService;

@Configurable
public class UserControllerHelper {
	
	@Autowired
	private UserService userService;

	public UserController createController() {
		return new UserController();
	}

	public void fillBasicUserData(UserController userController,
			String userEmail, String userPass) {
		userController.getUser().setEmail(userEmail);
		userController.getUser().setPassword(userPass);
		
	}


	public boolean isUserCreated(String newUserEmail, String newUserPass) {
		return userService.findUserByEmailAndPassword(newUserEmail, newUserPass) != null;
	}

}
