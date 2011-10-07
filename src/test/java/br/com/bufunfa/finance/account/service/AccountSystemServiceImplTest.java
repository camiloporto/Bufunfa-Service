package br.com.bufunfa.finance.account.service;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AccountSystemServiceImplTest {
	
	@Resource(name="accountService")
	private AccountSystemService accountService;
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper testHelper;// = new AccountSystemHelper();
	
	@Test
	public void testCreateNewAccountSystem_shouldCreateSuccess() {
		
		AccountSystem sample = testHelper
			.createValidSample()
			.getAccountSystem();
		
		accountService.saveAccountSystem(sample);
		AccountSystem saved = accountService.findAccountSystem(sample.getId());
		
		AccountSystem expected = new AccountSystemHelper()
			.createSample("AccountSystemSample", "sample")
			.getAccountSystem();
		
		Assert.assertNotNull("did not returned valid saved instance", saved);
		Assert.assertNotNull("did not assigned valid id", saved.getId());
		
		
		testHelper
			.verifyInitialAccountSystemState("account system initial state not satisfied", sample);
	}

}
