package br.com.bufunfa.finance.core.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AbstractMessageSource {
	
	@Autowired
	private MessageSource messageSource;
	
	private Locale locale = new Locale("pt", "BR");
	
	public String getMessage(String msgKey, Object[] msgArgs, Locale l) {
		return messageSource.getMessage(msgKey, msgArgs, l);
	}

}
