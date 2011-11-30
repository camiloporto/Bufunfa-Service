package br.com.bufunfa.finance.account.service.util;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.annotation.Resource;

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
	 * and adds one transaction from Income to Asset
	 * on 20/jan/2011 with value 100.00
	 * @return
	 */
	public AccountSystem prepareAccountSystemBasicSample() {
		AccountSystem as = accountSystemHelper.createAndSaveAccountSystemSample();
		transactionHelper.saveSampleTransactionFromLiabilityToAssetOnDate(
				as, 
				DateUtils.getDate(2011, Calendar.JANUARY, 20).getTime(), 
				null, 
				new BigDecimal("100.00"));
		return as;
	}

}
