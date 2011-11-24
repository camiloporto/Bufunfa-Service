package br.com.bufunfa.finance.account.service.validation;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.bufunfa.finance.account.modelo.Account;

public class AccountReportParameters {

	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_ACCOUNT.required}",
			groups={AccountReportConstraintGroups.ExtractRules.class})
	private Account account;
	
	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_BEGIN_DATE.required}",
			groups={AccountReportConstraintGroups.ExtractRules.class})
	private Date begin;
	
	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.EXTRACT_END_DATE.required}",
			groups={AccountReportConstraintGroups.ExtractRules.class})
	private Date end;
	
	public AccountReportParameters(Account account, Date begin, Date end) {
		this.account = account;
		this.begin = begin;
		this.end = end;
	}

}
