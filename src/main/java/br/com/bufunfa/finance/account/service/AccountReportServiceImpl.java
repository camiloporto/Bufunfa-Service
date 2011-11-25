package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountEntryRepository;
import br.com.bufunfa.finance.account.service.validation.AccountReportConstraintGroups;
import br.com.bufunfa.finance.account.service.validation.AccountReportParameters;
import br.com.bufunfa.finance.account.service.validation.ServiceValidator;

@Service
public class AccountReportServiceImpl implements AccountReportService {
	
	@Autowired
	private AccountEntryRepository accountEntryRepository;
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	private static Date MIN_DATE;
	
	static {
		Calendar c = Calendar.getInstance();
		int minYear = c.getActualMinimum(Calendar.YEAR);
		int minMonth = c.getActualMinimum(Calendar.MONTH);
		int minDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		
		MIN_DATE = new GregorianCalendar(minYear, minMonth, minDay).getTime();
	}
	
	@Override
	public AccountExtract getAccountExtract(Account account, Date begin,
			Date end) {
		
		validateExtractParameters(createForExtract(account, begin, end));
		
		List<AccountEntry> entries = accountEntryRepository.findByAccountIdAndDateBetween(account.getId(), begin, end);
		AccountExtract extract = new AccountExtract(entries);
		
		return extract;
	}
	
	public BigDecimal getAccountBalance(Account account, Date date) {
		//ajustar metodo para nao ter limit einferior
		return accountEntryRepository.getAccountBalance(account.getId(), date);
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
	
	
	private BigDecimal sumEntries(List<AccountEntry> entries) {
		BigDecimal sum = new BigDecimal("0.00");
		for (AccountEntry ae : entries) {
			sum = sum.add(ae.getValue());
		}
		return sum;
	}

}
