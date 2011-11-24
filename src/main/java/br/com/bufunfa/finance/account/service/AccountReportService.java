package br.com.bufunfa.finance.account.service;

import java.util.Date;

import br.com.bufunfa.finance.account.modelo.Account;

public interface AccountReportService {

	AccountExtract getAccountExtract(Account account, Date begin, Date end);

}
