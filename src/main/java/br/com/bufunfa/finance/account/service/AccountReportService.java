package br.com.bufunfa.finance.account.service;

import java.util.Date;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

public interface AccountReportService {

	AccountExtract getAccountExtract(Account account, Date begin, Date end);

	BalanceSheet getBalanceSheet(AccountSystem accountSystem, Date date);

}
