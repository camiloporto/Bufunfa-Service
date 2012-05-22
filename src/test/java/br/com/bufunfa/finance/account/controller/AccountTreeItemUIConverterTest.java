package br.com.bufunfa.finance.account.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;

public class AccountTreeItemUIConverterTest {
	
	@Test
	public void testGetAsString_shouldGetAccountId() {
		String accountName = "SomeName";
		Long id = 1L;
		AccountTreeItemUI accountItem = createMockedAccountItem(accountName, id);
		AccountTreeItemUIConverter converter = createAccountTreeItemUIConverterForTest();
		
		String asString = converter.getAsString(null, null, accountItem);
		
		Assert.assertEquals("expected account id not returned", id.toString(), asString);
		
	}
	
	@Test
	public void testGetAsObject_shouldGetAccountItemById() {
		String accountName = "SomeName";
		Long id = 1L;
		AccountController accountController = getMockedAccountController();
		AccountTreeItemUI accountItem = createMockedAccountItem(accountName, id);
		when(accountController.findAccountItemById(id)).thenReturn(accountItem);
		
		AccountTreeItemUIConverter converter = createAccountTreeItemUIConverterForTest();
		converter.setAccountController(accountController);
		
		AccountTreeItemUI converted = (AccountTreeItemUI) converter.getAsObject(null, null, id.toString());
		
		verify(accountController).findAccountItemById(id);
		Assert.assertEquals("expected account name not returned", accountName, converted.getAccountName());
		
		
	}

	
	private AccountTreeItemUI createMockedAccountItem(String accountName, Long id) {
		AccountTreeItemUI item = mock(AccountTreeItemUI.class);
		when(item.getAccountName()).thenReturn(accountName);
		when(item.getId()).thenReturn(id);
		
		return item;
	}


	private AccountTreeItemUIConverter createAccountTreeItemUIConverterForTest() {
		AccountTreeItemUIConverter converter = new AccountTreeItemUIConverter();
		return converter;
	}
	
	private AccountController getMockedAccountController() {
		return mock(AccountController.class);
	}
	
}
