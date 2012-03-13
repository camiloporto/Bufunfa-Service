package br.com.bufunfa.finance.account.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;

public class AccountControllerTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private AccountController accountController;
	
	@Test
	public void testAddNewAccount_shouldSuccess() {
	}

}
