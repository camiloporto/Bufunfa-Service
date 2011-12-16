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
		
		validateExtractParameters(createReportParameters(account, begin, end));
		
		List<AccountEntry> entries = accountEntryRepository.findByAccountIdAndDateBetween(account.getId(), begin, end);
		AccountExtract extract = new AccountExtract(entries);
		
		return extract;
	}
	
	public BigDecimal getAccountBalance(Account account, Date date) {
	
		validateAccountBalanceParameters(createReportParameters(account, date));
		
		Set<Long> accountIdsOnTree = findChildrenAccountIdsOf(account);
		BigDecimal result = accountEntryRepository.getDeepAccountBalance(accountIdsOnTree, date);
		if(result == null) {
			return new BigDecimal("0.00");
		}
		return result;
	}
	
	public BalanceSheet getBalanceSheet(AccountSystem accountSystem, Date date) {
		
		Account asset = accountSystemService.findAssetAccount(accountSystem);
		Account liability = accountSystemService.findLiabilityAccount(accountSystem);
		BigDecimal assetBalance = getAccountBalance(asset, date);
		BigDecimal liabilityBalance = getAccountBalance(liability, date);
		BalanceSheet bs = new BalanceSheet(assetBalance, liabilityBalance, date);
		return bs;
	}
	
	public BalanceSheet getBalanceSheetTree(AccountSystem accountSystem, Date date) {
		Account root = accountSystemService.findAccount(accountSystem.getRootAccountId());
		Account asset = accountSystemService.findAssetAccount(accountSystem);
		Account liability = accountSystemService.findLiabilityAccount(accountSystem);
		BigDecimal assetBalance = getAccountBalance(asset, date);
		BigDecimal liabilityBalance = getAccountBalance(liability, date);
		
		BalanceSheet bs = new BalanceSheet(root);
		bs.getRootNode().addChild(asset, assetBalance, date);
		bs.getRootNode().addChild(liability, liabilityBalance, date);
		return bs;
	}
	
	public BalanceSheet getOperatingCashBalanceTree(AccountSystem accountSystem, Date date) {
		Account root = accountSystemService.findAccount(accountSystem.getRootAccountId());
		Account income = accountSystemService.findIncomeAccount(accountSystem);
		Account outcome = accountSystemService.findOutcomeAccount(accountSystem);
		BigDecimal incomeBalance = getAccountBalance(income, date);
		BigDecimal outcomeBalance = getAccountBalance(outcome, date);
		
		BalanceSheet bs = new BalanceSheet(root);
		bs.getRootNode().addChild(income, incomeBalance, date);
		bs.getRootNode().addChild(outcome, outcomeBalance, date);
		return bs;
	}
	
	private AccountReportParameters createReportParameters(Account account, Date begin,
			Date end) {
		return new AccountReportParameters(account, begin, end);
	}
	private AccountReportParameters createReportParameters(Account account, Date date) {
		return new AccountReportParameters(account, null, date);
	}
	
	private void validateExtractParameters(AccountReportParameters p) {
		new ServiceValidator().validate(p, AccountReportConstraintGroups.ExtractRules.class);
	}
	
	private void validateAccountBalanceParameters(AccountReportParameters p) {
		new ServiceValidator().validate(p, AccountReportConstraintGroups.AccountBalanceRules.class);
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
