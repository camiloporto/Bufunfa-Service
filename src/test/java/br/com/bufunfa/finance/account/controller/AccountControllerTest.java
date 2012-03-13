package br.com.bufunfa.finance.account.controller;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.controller.UserController;
import br.com.bufunfa.finance.user.modelo.User;

public class AccountControllerTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private AccountController accountController;
	
	@Autowired
	private UserController userController;
	
	private User sampleUser;
	
	@Before
	public void loginSampleUser() {
		sampleUser = generateAndSaveSampleUser();
		loginSampleUser(sampleUser);
	}
	
	@After
	public void logoutSampleUser() {
		logoutLoggedUser();
	}
	
	@Test
	public void testAddNewAccount_shouldSuccess() {
		
		AccountTreeItemUI selectedItem = accountController.findLeafItemByName("ativos");
		accountController.getAccountTree().setSelectedItem(selectedItem);
		accountController.getAccountTree().setEditing(false);
		String accountName = "NewName";
		accountController.getAccountTree().getEditingItem().setAccountName(accountName);
		
		accountController.saveItem();
		
		AccountTreeItemUI savedItem = accountController.findLeafItemByName(accountName);
		Assert.assertNotNull("new account dit not saved", savedItem);
	}
	
	@Test
	public void testInitialLoadOfTwoLevelAccount_shouldLoadInitialyTwoLevelOfAccountHierarchy() {
		//selecionar a conta pai.
		AccountTreeItemUI selectedItem = accountController.findLeafItemByName("ativos");
		accountController.getAccountTree().setSelectedItem(selectedItem);
		accountController.getAccountTree().setEditing(false);
		String accountName = "NewName2";
		accountController.getAccountTree().getEditingItem().setAccountName(accountName);
		
		accountController.saveItem();
		logoutLoggedUser();
		loginSampleUser(this.sampleUser);
		
		AccountTreeItemUI savedItem = accountController.findLeafItemByName(accountName);
		Assert.assertNotNull("new account dit not loaded", savedItem);
	}
	
	@Test
	public void testInitialLoadOfThreeLevelAccount_shouldLoadInitialyThreeLevelAccountHierarchy() {

		AccountTreeItemUI selectedItem = accountController.findLeafItemByName("ativos");
		accountController.getAccountTree().setSelectedItem(selectedItem);
		accountController.getAccountTree().setEditing(false);
		String accountName = "NewName3";
		accountController.getAccountTree().getEditingItem().setAccountName(accountName);
		accountController.saveItem();
		
		AccountTreeItemUI savedItem = accountController.findLeafItemByName(accountName);
		accountController.getAccountTree().setSelectedItem(savedItem);
		accountController.getAccountTree().setEditing(false);
		accountName = "NewName3Child";
		accountController.getAccountTree().getEditingItem().setAccountName(accountName);
		accountController.saveItem();
		
		logoutLoggedUser();
		loginSampleUser(this.sampleUser);
		
		AccountTreeItemUI savedChildItem = accountController.findLeafItemByName(accountName);
		Assert.assertNotNull("third level account dit not loaded", savedChildItem);
	}
	
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
	
}
