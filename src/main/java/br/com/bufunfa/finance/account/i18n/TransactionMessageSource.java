package br.com.bufunfa.finance.account.i18n;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.core.i18n.AbstractMessageSource;

@Configurable
@Component
public class TransactionMessageSource extends AbstractMessageSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8367128120294053140L;
	
	public static final String TRANSACTION_ADDED_SUCCESSFULLY = "TRANSACTION_ADDED_SUCCESSFULLY";

	public static final String TRANSACTION_UPDATED_SUCCESSFULLY = "TRANSACTION_UPDATED_SUCCESSFULLY";

	public static final String TRANSACTION_DELETED_SUCCESSFULLY = "TRANSACTION_DELETED_SUCCESSFULLY";

}
