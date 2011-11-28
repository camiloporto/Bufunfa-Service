package br.com.bufunfa.finance.account.service;

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
		Date date = DateUtils.getDate(2011, Calendar.JANUARY, 30).getTime();
		
		BalanceSheet balanceTree = accountBalanceNavigator.getBalanceSheetTree(as, date);
		BalanceSheetNode rootNode = balanceTree.getRootNode();
		
		Assert.assertNotNull("rootNode should not be null", rootNode);
		Assert.assertEquals("rootNode name did not match", as.getName(), rootNode.getName());
		
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
		
		Assert.assertThat("node1 is not asset or liability", 
				node1.getName(), 
				AnyOf.anyOf(IsEqual.equalTo(Account.ASSET_NAME), IsEqual.equalTo(Account.LIABILITY_NAME)));
		Assert.assertThat("node2 is not asset or liability", 
				node2.getName(), 
				AnyOf.anyOf(IsEqual.equalTo(Account.ASSET_NAME), IsEqual.equalTo(Account.LIABILITY_NAME)));
		
	}
	
	private boolean isIn(String key, Collection<String> values) {
		for (String s : values) {
			if(s.equals(key))
				return true;
		}
		return false;
	}

}
