package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJavaBean
public class BalanceSheet {

	private BigDecimal assetBalance;
	private BigDecimal liabilityBalance;
	private Date date;
	
	private BalanceSheetNode rootNode;
	
	public BalanceSheet(BigDecimal assetBalance, BigDecimal liabilityBalance,
			Date date) {
		this.assetBalance = assetBalance;
		this.liabilityBalance = liabilityBalance;
		this.date = date;
	}
	
	public BalanceSheet(Account root) {
		this.rootNode = new BalanceSheetNode(root, null);
	}
}
