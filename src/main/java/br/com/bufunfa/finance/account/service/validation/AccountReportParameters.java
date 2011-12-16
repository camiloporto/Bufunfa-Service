package br.com.bufunfa.finance.account.service.validation;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

public class AccountReportParameters {

	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT.required}",
			groups={
				AccountReportConstraintGroups.ExtractRules.class,
				AccountReportConstraintGroups.AccountBalanceRules.class})
	private Account account;
	
	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT_SYSTEM.required}",
			groups={AccountReportConstraintGroups.BalanceSheetRules.class})
	private AccountSystem accountSystem;
	
	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.BEGIN_DATE.required}",
			groups={AccountReportConstraintGroups.ExtractRules.class})
	private Date begin;
	
	@NotNull(message="{br.com.bufunfa.finance.service.AccountReportService.END_DATE.required}",
			groups={
				AccountReportConstraintGroups.ExtractRules.class,
				AccountReportConstraintGroups.AccountBalanceRules.class})
	private Date end;
	
	public AccountReportParameters(Account account, Date begin, Date end) {
		this.account = account;
		this.begin = begin;
		this.end = end;
	}
	
	public AccountReportParameters(AccountSystem accountSystem, Date date) {
		this.accountSystem = accountSystem;
		this.end = date;
	}

	@AssertFalse(message="{br.com.bufunfa.finance.service.AccountReportService.ACCOUNT.required}",
			groups={
			AccountReportConstraintGroups.AccountBalanceRules.class})
	public boolean isAccountTransient() {
		if(account != null) {
			return this.account.getId() == null;
		}
		return false;
	}

}
