package br.com.bufunfa.finance.user.i18n;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.core.i18n.AbstractMessageSource;

@Configurable
@Component
public class UserMessageSource extends AbstractMessageSource {
	
	
	public static final String USER_ADDED_SUCCESSFULLY = "USER_ADDED_SUCCESSFULLY";
	public static final String USER_CREDENTIALS_FAILED = "USER_CREDENTIALS_FAILED";
	

}
