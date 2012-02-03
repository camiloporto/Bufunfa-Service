package br.com.bufunfa.finance.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.user.modelo.User;

public class UserServiceImplTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testSaveNewUser_shouldSuccess() {
		User u = new User();
		u.setEmail("newuser@email.com");
		u.setPassword("secret");
		
		userService.saveUser(u);
		
		User saved = userService.findUser(u.getId());
		
		Assert.assertNotNull("did not save the new user", saved);
		Assert.assertNotNull("did not assigned valid id", saved.getId());
	}

}
