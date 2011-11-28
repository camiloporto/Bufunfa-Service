package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

public interface AccountReportService {

	AccountExtract getAccountExtract(Account account, Date begin, Date end);

	BalanceSheet getBalanceSheet(AccountSystem accountSystem, Date date);

	BigDecimal getAccountBalance(Account account, Date date);

	BalanceSheet getBalanceSheetTree(AccountSystem accountSystem, Date date);

}
