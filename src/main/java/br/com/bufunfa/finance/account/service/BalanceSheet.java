package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class BalanceSheet {

	private BigDecimal assetBalance;
	private BigDecimal liabilityBalance;
	private Date date;
	
	public BalanceSheet(BigDecimal assetBalance, BigDecimal liabilityBalance,
			Date date) {
		this.assetBalance = assetBalance;
		this.liabilityBalance = liabilityBalance;
		this.date = date;
	}
}
