package br.com.bufunfa.finance.user.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class UserMessageSource {
	
	@Autowired
	private MessageSource messageSource;
	
	private Locale locale = new Locale("pt", "BR");
	
	public static final String USER_ADDED_SUCCESSFULLY = "USER_ADDED_SUCCESSFULLY";
	public static final String USER_CREDENTIALS_FAILED = "USER_CREDENTIALS_FAILED";
	
	
	public String getMessage(String msgKey, Object[] msgArgs, Locale l) {
		return messageSource.getMessage(msgKey, msgArgs, l);
	}
	

}
