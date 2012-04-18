package br.com.bufunfa.finance.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.controller.UserController;
import br.com.bufunfa.finance.user.modelo.User;

@Component
public class AccountControllerHelper {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private AccountController accountController;
	
	User generateAndSaveSampleUser() {
		String email = TestDataGenerator.generateValidEmail();
		User u = new User();
		u.setEmail(email);
		u.setPassword("secret");
		
		userController.getUser().setEmail(u.getEmail());
		userController.getUser().setPassword(u.getPassword());
		userController.saveNewUser();
		return u;
	}
	
	void loginSampleUser(User u) {
		userController.getUser().setEmail(u.getEmail());
		userController.getUser().setPassword(u.getPassword());
		userController.loginUser();
	}
	
	void logoutLoggedUser() {
		userController.logoutUser();
	}
	
	public Account findAccountByName(String name) {
		AccountTreeItemUI item = accountController.findLeafItemByName(name);
		return item.getAccount();
	}

}
