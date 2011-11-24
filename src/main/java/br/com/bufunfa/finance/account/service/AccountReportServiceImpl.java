package br.com.bufunfa.finance.account.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.repository.AccountEntryRepository;
import br.com.bufunfa.finance.account.service.validation.AccountReportConstraintGroups;
import br.com.bufunfa.finance.account.service.validation.AccountReportParameters;
import br.com.bufunfa.finance.account.service.validation.ServiceValidator;
import br.com.bufunfa.finance.account.service.validation.TransactionConstraintGroups;

@Service
public class AccountReportServiceImpl implements AccountReportService {
	
	@Autowired
	private AccountEntryRepository accountEntryRepository;
	
	@Override
	public AccountExtract getAccountExtract(Account account, Date begin,
			Date end) {
		
		validateExtractParameters(createForExtract(account, begin, end));
		
		List<AccountEntry> entries = accountEntryRepository.findByAccountIdAndDateBetween(account.getId(), begin, end);
		AccountExtract extract = new AccountExtract(entries);
		
		return extract;
	}
	
	private AccountReportParameters createForExtract(Account account, Date begin,
			Date end) {
		return new AccountReportParameters(account, begin, end);
	}
	
	private void validateExtractParameters(AccountReportParameters p) {
		new ServiceValidator().validate(p, AccountReportConstraintGroups.ExtractRules.class);
	}

}
