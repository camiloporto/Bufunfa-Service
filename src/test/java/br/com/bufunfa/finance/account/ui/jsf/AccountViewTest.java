package br.com.bufunfa.finance.account.ui.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.account.ui.jsf.config.AccountViewNames;
import br.com.bufunfa.finance.core.ui.jsf.util.Property2BeanUtil;

public class AccountViewTest extends SpringRootTestsConfiguration {
	
	private AccountViewNames accountViewNames;
	
	public AccountViewTest() throws IOException, IllegalAccessException, InvocationTargetException {
		accountViewNames = new AccountViewNames();
		Property2BeanUtil.fillBeanWithProperties(
				accountViewNames, 
				AccountViewNames.class.getCanonicalName());
	}
	
	

}
