package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountEntryRepository;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.service.validation.AccountReportConstraintGroups;
import br.com.bufunfa.finance.account.service.validation.AccountReportParameters;
import br.com.bufunfa.finance.account.service.validation.ServiceValidator;

@Service
public class AccountReportServiceImpl implements AccountReportService {
	
	@Autowired
	private AccountEntryRepository accountEntryRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	@Override
	public AccountExtract getAccountExtract(Account account, Date begin,
			Date end) {
		
		validateExtractParameters(createForExtract(account, begin, end));
		
		List<AccountEntry> entries = accountEntryRepository.findByAccountIdAndDateBetween(account.getId(), begin, end);
		AccountExtract extract = new AccountExtract(entries);
		
		return extract;
	}
	
	public BigDecimal getAccountBalance(Account account, Date date) {
	
		Set<Long> accountIdsOnTree = findChildrenAccountIdsOf(account);
		return accountEntryRepository.getDeepAccountBalance(accountIdsOnTree, date);
	}
	
	public BalanceSheet getBalanceSheet(AccountSystem accountSystem, Date date) {
		
		Account asset = accountSystemService.findAssetAccount(accountSystem);
		Account liability = accountSystemService.findLiabilityAccount(accountSystem);
		BigDecimal assetBalance = getAccountBalance(asset, date);
		BigDecimal liabilityBalance = getAccountBalance(liability, date);
		BalanceSheet bs = new BalanceSheet(assetBalance, liabilityBalance, date);
		return bs;
	}
	
	private AccountReportParameters createForExtract(Account account, Date begin,
			Date end) {
		return new AccountReportParameters(account, begin, end);
	}
	
	private void validateExtractParameters(AccountReportParameters p) {
		new ServiceValidator().validate(p, AccountReportConstraintGroups.ExtractRules.class);
	}
	
	private Set<Long> findChildrenAccountIdsOf(Account a) {
		Set<Long> result = new HashSet<Long>();
		return findChildrenAccountIdsOf(result, a);
	}
	
	private Set<Long> findChildrenAccountIdsOf(Set<Long> result, Account a) {
		List<Account> children = accountRepository.findByFatherId(a.getId());
		result.add(a.getId());
		for (Account account : children) {
			findChildrenAccountIdsOf(result, account);
		}
		
		return result;
	}
}
