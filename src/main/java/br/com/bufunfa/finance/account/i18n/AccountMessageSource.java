package br.com.bufunfa.finance.account.i18n;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.core.i18n.AbstractMessageSource;

@Configurable
@Component
public class AccountMessageSource extends AbstractMessageSource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4293677574504692365L;
	
	public static final String ACCOUNT_REMOVED_SUCCESSFULLY = "ACCOUNT_REMOVED_SUCCESSFULLY";
	public static final String ACCOUNT_ADDED_SUCCESSFULLY = "ACCOUNT_ADDED_SUCCESSFULLY";
	public static final String ACCOUNT_UPDATED_SUCCESSFULLY = "ACCOUNT_UPDATED_SUCCESSFULLY";

}
