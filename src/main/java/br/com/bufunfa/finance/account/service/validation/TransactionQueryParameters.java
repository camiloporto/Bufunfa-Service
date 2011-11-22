package br.com.bufunfa.finance.account.service.validation;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class TransactionQueryParameters {
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.TRANSACTION_QUERY_BEGIN_DATE.required}",
			groups={TransactionConstraintGroups.FindByDateBetweenRules.class})
	private Date beginDate;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.TRANSACTION_QUERY_END_DATE.required}",
			groups={TransactionConstraintGroups.FindByDateBetweenRules.class})
	private Date endDate;

}
