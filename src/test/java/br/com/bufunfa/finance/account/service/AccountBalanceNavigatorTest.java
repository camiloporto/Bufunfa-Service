package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.util.AccountBalanceNavigatorHelper;
import br.com.bufunfa.finance.account.service.util.DateUtils;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;


public class AccountBalanceNavigatorTest extends SpringRootTestsConfiguration {
	
	@Autowired
	private AccountBalanceNavigatorHelper accountBalanceNavigatorHelper;
	
	@Autowired
	private AccountReportService accountBalanceNavigator;
	
	@Autowired
	private AccountSystemService accountService;
	
	@Test
	public void testGetRootNode_shouldSuccess() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Account root = accountService.findAccount(as.getRootAccountId());
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		
		Assert.assertNotNull("rootNode should not be null", rootNode);
		Assert.assertEquals("rootNode name did not match", as.getName(), rootNode.getName());
		Assert.assertEquals("rootNode id did not match", root.getId(), rootNode.getId());
		
	}
	
	@Test
	public void testGetRootNodeChildren_shouldGet1stLevelAccountNodes() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		Collection<BalanceSheetNode> children = rootNode.getChildren();
		
		//in balance sheet, only assets and liabilities are shown
		int expectedChildrenCount = 2;
		
		Assert.assertNotNull("rootNode children should not be null", rootNode);
		Assert.assertEquals("children count did not match", expectedChildrenCount, children.size());
		
		Iterator<BalanceSheetNode> it = children.iterator();
		BalanceSheetNode node1 = it.next();
		BalanceSheetNode node2 = it.next();
		
		Assert.assertThat("node1 is asset or liability", 
				node1.getName(), 
				AnyOf.anyOf(IsEqual.equalTo(Account.ASSET_NAME), IsEqual.equalTo(Account.LIABILITY_NAME)));
		Assert.assertThat("node2 is asset or liability", 
				node2.getName(), 
				AnyOf.anyOf(IsEqual.equalTo(Account.ASSET_NAME), IsEqual.equalTo(Account.LIABILITY_NAME)));
		
		Assert.assertThat("node1 father is rootNode", 
				node1.getFatherId(), 
				IsEqual.equalTo(rootNode.getId()));
		
		Assert.assertThat("node2 father is rootNode", 
				node2.getFatherId(), 
				IsEqual.equalTo(rootNode.getId()));
	}
	
	@Test
	public void testGetAssetNodeBalance_shouldGetAssetNodeBalanceValue() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		
		BalanceSheetNode assetNode = rootNode.getChildByName(Account.ASSET_NAME);
		BigDecimal expectedBalance = new BigDecimal("100.00");
		
		Assert.assertThat("assetNode balance is 100.00", 
				assetNode.getBalance(), 
				IsEqual.equalTo(expectedBalance));
		
	}
	
	@Test
	public void testGetLiabilityNodeBalance_shouldGetLiabilityNodeBalanceValue() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		
		BalanceSheetNode liabilityNode = rootNode.getChildByName(Account.LIABILITY_NAME);
		BigDecimal expectedBalance = new BigDecimal("-100.00");
		
		Assert.assertThat("liabilityNode balance is -100.00", 
				liabilityNode.getBalance(), 
				IsEqual.equalTo(expectedBalance));
		
	}
	
	@Test
	public void testGetAssetNodeChildBalance_shouldGetAssetNodeChildBalanceValue() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		Account liabilityAccount = accountService.findLiabilityAccount(as);
		Account assetAccount = accountService.findAssetAccount(as);
		String assetChildName = "Cash";
		Account assetNodeChild = accountBalanceNavigatorHelper.saveAccountSample(assetChildName, assetAccount.getId());
		
		BigDecimal transactionValue = new BigDecimal("50.00");
		accountBalanceNavigatorHelper.saveSampleTransaction(liabilityAccount, assetNodeChild, date, transactionValue);
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		BalanceSheetNode assetNode = rootNode.getChildByName(Account.ASSET_NAME);
		BalanceSheetNode cashNode = assetNode.getChildByName(assetChildName);
		
		BigDecimal expectedBalance = new BigDecimal("50.00");
		
		Assert.assertNotNull("cashNode should not be null", cashNode);
		Assert.assertThat("cashNode balance is 50.00", 
				cashNode.getBalance(), 
				IsEqual.equalTo(expectedBalance));
		
	}
	
	@Test
	public void testGetLiabilityNodeChildBalance_shouldGetLiabilityNodeChildBalanceValue() {
		AccountSystem as = accountBalanceNavigatorHelper.prepareAccountSystemBasicSample();
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		Account liabilityAccount = accountService.findLiabilityAccount(as);
		Account assetAccount = accountService.findAssetAccount(as);
		String liabilityChildName = "Dad";
		Account liabilityNodeChild = accountBalanceNavigatorHelper.saveAccountSample(liabilityChildName, liabilityAccount.getId());
		
		BigDecimal transactionValue = new BigDecimal("50.00");
		accountBalanceNavigatorHelper.saveSampleTransaction(liabilityNodeChild, assetAccount, date, transactionValue);
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		BalanceSheetNode liabilityNode = rootNode.getChildByName(Account.LIABILITY_NAME);
		BalanceSheetNode dadNode = liabilityNode.getChildByName(liabilityChildName);
		
		BigDecimal expectedBalance = new BigDecimal("-50.00");
		
		Assert.assertNotNull("dadNode should not be null", dadNode);
		Assert.assertThat("dadNode balance is -50.00", 
				dadNode.getBalance(), 
				IsEqual.equalTo(expectedBalance));
		
	}

}
