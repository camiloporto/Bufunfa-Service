package br.com.bufunfa.finance.account.service;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountSystemHelper;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;

public class AccountTreeTest extends SpringRootTestsConfiguration{
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper serviceTestHelper;
	
	@Autowired
	private AccountSystemService accountService;
	
	@Test
	public void testGetAccountTree_shouldSuccess() {
		AccountSystem as = serviceTestHelper.createAndSaveAccountSystemSample();
		Account rootAccount = accountService.findAccount(as.getRootAccountId());
		
		AccountTree tree = new AccountTree(as, rootAccount);
		
		AccountTreeNode root = tree.getRootNode();
		Assert.assertNotNull("root node not find: null", root);
		Assert.assertEquals("root node did not match accountSystem root account", 
				as.getRootAccountId(), 
				root.getAccount().getId().longValue());
	}
	
	@Test
	public void testGetAssetNode_shouldSuccess() {
		AccountTree tree = createSampleAccountTree();
		
		AccountTreeNode assetNode = tree.getAssetNode();
		
		Assert.assertEquals("asset node name did not match", 
				Account.ASSET_NAME, 
				assetNode.getAccount().getName());
	}
	
	@Test
	public void testGetLiabilityNode_shouldSuccess() {
		AccountTree tree = createSampleAccountTree();
		
		AccountTreeNode liabilityNode = tree.getLiabilityNode();
		
		Assert.assertEquals("liability node name did not match", 
				Account.LIABILITY_NAME, 
				liabilityNode.getAccount().getName());
	}
	
	@Test
	public void testGetIncomeNode_shouldSuccess() {
		AccountTree tree = createSampleAccountTree();
		
		AccountTreeNode incomeNode = tree.getIncomeNode();
		
		Assert.assertEquals("income node name did not match", 
				Account.INCOME_NAME, 
				incomeNode.getAccount().getName());
	}
	
	@Test
	public void testGetOutcomeNode_shouldSuccess() {
		AccountTree tree = createSampleAccountTree();
		
		AccountTreeNode outcomeNode = tree.getOutcomeNode();
		
		Assert.assertEquals("outcome node name did not match", 
				Account.OUTCOME_NAME, 
				outcomeNode.getAccount().getName());
	}
	
	@Test
	public void testGetAssetChildren_shouldSuccess() {
		AccountSystem as = serviceTestHelper.createAndSaveAccountSystemSample();
		Account rootAccount = accountService.findAccount(as.getRootAccountId());

		AccountTree tree = new AccountTree(as, rootAccount);
		
		Set<AccountTreeNode> children = tree.getRootNode().getChildren();
		
		final int expectedChildrenCount = 4;
		Assert.assertEquals("children count did not match", 
				expectedChildrenCount, 
				children.size());
		
		Iterator<AccountTreeNode> it = children.iterator();
		AccountTreeNode first = it.next();
		AccountTreeNode second = it.next();
		AccountTreeNode third = it.next();
		AccountTreeNode fourth = it.next();
		
		Assert.assertEquals("first children name did not match", 
				Account.ASSET_NAME, 
				first.getAccount().getName());
		
		Assert.assertEquals("second children name did not match", 
				Account.INCOME_NAME, 
				second.getAccount().getName());
		
		Assert.assertEquals("third children name did not match", 
				Account.LIABILITY_NAME, 
				third.getAccount().getName());
		
		Assert.assertEquals("fourth children name did not match", 
				Account.OUTCOME_NAME, 
				fourth.getAccount().getName());
	}
	
	AccountTree createSampleAccountTree() {
		AccountSystem as = serviceTestHelper.createAndSaveAccountSystemSample();
		Account rootAccount = accountService.findAccount(as.getRootAccountId());

		AccountTree tree = new AccountTree(as, rootAccount);
		
		return tree;
	}

}
