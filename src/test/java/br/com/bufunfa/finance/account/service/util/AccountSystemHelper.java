package br.com.bufunfa.finance.account.service.util;

import java.util.List;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.service.AccountSystemService;

@Configurable
public class AccountSystemHelper {
	
	private AccountSystem accountSystem = new AccountSystem();
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountSystemService accountService;
	
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public AccountSystem createAndSaveAccountSystemSample() {
		AccountSystem as = createValidSample()
			.getAccountSystem();
		accountService.saveAccountSystem(as);
		return as;
	}
	
	/**
	 * Create a valid AccountSystem sample with
	 * name: AccountSystemSample
	 * userId: sample
	 * @return
	 */
	public AccountSystemHelper createValidSample() {
		return generateValidAccountSystemSample();
	}
	
	/**
	 * Create a valid AccountSystem sample with
	 * name: AccountSystemSample
	 * userId: sample
	 * @return
	 */
	private AccountSystemHelper generateValidAccountSystemSample() {
		accountSystem = new AccountSystem();
		accountSystem.setName("AccountSystemSample" + System.currentTimeMillis());
		accountSystem.setUserId("sample" + System.currentTimeMillis());
		
		return this;
	}
	
	public AccountSystemHelper createSample(String name, String userId) {
		accountSystem = new AccountSystem();
		accountSystem.setName(name);
		accountSystem.setUserId(userId);
		
		return this;
	}
	
	public AccountSystem getAccountSystem() {
		return this.accountSystem;
	}

	public void verifyInitialAccountSystemState(String testMsg,
			AccountSystem sample) {
		Long rootAccountId = sample.getRootAccountId();
		Assert.assertTrue(
				testMsg + "- rootAccountId not assigned: " + rootAccountId, 
				rootAccountId > 0);
		
		verifyInitialAccountHierarchy(testMsg, sample);
		
	}

	private void verifyInitialAccountHierarchy(String testMsg, AccountSystem sample) {
		verifyRootAccountInitialState(testMsg, sample);
		verifyFirstChildrenAccount(testMsg, sample);
	}

	
	private void verifyFirstChildrenAccount(String testMsg, AccountSystem sample) {
		Account root = accountRepository.findOne(sample.getRootAccountId());
		List<Account> children = accountRepository.findByFatherId(root.getId());
		
		int expectedChildrenCount = 4;
		int actualChildrenCount = children.size();
		Assert.assertEquals(
				testMsg + " - children count != 4 : " + actualChildrenCount, 
				expectedChildrenCount,
				actualChildrenCount);
		
		verifyAccountNameIsIn(
				testMsg,
				"br.com.bufunfa.finance.modelo.account.INCOME_ACCOUNT_NAME",
				children);
		
		verifyAccountNameIsIn(
				testMsg,
				"br.com.bufunfa.finance.modelo.account.OUTCOME_ACCOUNT_NAME",
				children);
		
		verifyAccountNameIsIn(
				testMsg,
				"br.com.bufunfa.finance.modelo.account.ASSET_ACCOUNT_NAME",
				children);
		
		verifyAccountNameIsIn(
				testMsg,
				"br.com.bufunfa.finance.modelo.account.LIABILITY_ACCOUNT_NAME",
				children);
		
	}

	private void verifyAccountNameIsIn(String testMsg, String accountName,
			List<Account> children) {
		for (Account account : children) {
			if(accountName.equals(account.getName())) {
				return;
			}
		}
		Assert.fail(testMsg + " account expected not found " + accountName);
	}

	private void verifyRootAccountInitialState(String testMsg, AccountSystem sample) {
		Account rootAccount = accountRepository.findOne(sample.getRootAccountId());
		String expectedAccountName = sample.getName();
		Assert.assertEquals(testMsg + " - rootAccountName failed ", expectedAccountName, rootAccount.getName());
		Assert.assertNull(testMsg + " - rootAccount father id not null", rootAccount.getFatherId());
	}

}
