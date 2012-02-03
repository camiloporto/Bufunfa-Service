package br.com.bufunfa.finance.user.controller;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.user.controller.util.UserControllerHelper;

public class UserControllerTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private UserController userController;
	
	@Resource(name="userControllerHelper")
	private UserControllerHelper userControllerHelper;
	
	@Test
	public void testCreateNewUser_shouldSuccess() {
//		userController = userControllerHelper.createController();
		String newUserEmail = "new@email.com";
		String newUserPass = "secret";
		userControllerHelper.fillBasicUserData(
				userController,
				newUserEmail,
				newUserPass
				);
		userController.saveNewUser();
		
		Assert.assertTrue(
				"new User not created",
				userControllerHelper.isUserCreated(newUserEmail, newUserPass));
		
	}

}
