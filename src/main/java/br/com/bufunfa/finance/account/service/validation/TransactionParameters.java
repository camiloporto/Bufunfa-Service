package br.com.bufunfa.finance.account.service.validation;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TransactionParameters {
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT_ID.required}", 
			groups={SaveTransactionValidationRules.class})
	private Long originAccountId;

}
