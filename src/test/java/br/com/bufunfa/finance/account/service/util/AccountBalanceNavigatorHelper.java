package br.com.bufunfa.finance.account.service.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

public class AccountBalanceNavigatorHelper {
	
	@Resource(name="accountSystemServiceHelper")
	private AccountSystemHelper accountSystemHelper;
	
	@Resource(name="transactionHelper")
	private TransactionHelper transactionHelper;
	
	@Resource(name="accountHelper")
    private AccountHelper accountHelper;
	
	
	/**
	 * Prepare an account system with basic accounts
	 * and adds one transaction from Liability to Asset
	 * on 20/jan/2011 with value 100.00
	 * @return
	 */
	public AccountSystem prepareAccountSystemBasicSampleForBalanceSheet() {
		AccountSystem as = accountSystemHelper.createAndSaveAccountSystemSample();
		transactionHelper.saveSampleTransactionFromLiabilityToAssetOnDate(
				as, 
				DateUtils.getDate(2011, Calendar.JANUARY, 20).getTime(), 
				null, 
				new BigDecimal("100.00"));
		return as;
	}
	
	/**
	 * Prepare an account system with basic accounts
	 * and adds one transaction from Income to Outcome
	 * on 20/jan/2011 with value 200.00
	 * @return
	 */
	public AccountSystem prepareAccountSystemBasicSampleForCashflow() {
		AccountSystem as = accountSystemHelper.createAndSaveAccountSystemSample();
		transactionHelper.saveSampleTransactionFromIncomeToOutcomeOnDate(
				as, 
				DateUtils.getDate(2011, Calendar.JANUARY, 20).getTime(), 
				null, 
				new BigDecimal("200.00"));
		return as;
	}


	public Account saveAccountSample(String name, Long fatherId) {
		return accountHelper.saveAccountSample(name, fatherId);
	}


	public void saveSampleTransaction(Account from, Account to, Date date,
			BigDecimal value) {
		transactionHelper.saveSampleTransaction(from, to, date, value);
	}
	
	

}
