package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean(settersByDefault=false)
public class UIExtractItem {
	
	private Date date;
	private String description;
	private String originAccountName;
	private String destAccountName;
	private BigDecimal value;
	
	UIExtractItem(Date date, String description,
			String originAccountName, String destAccountName, BigDecimal value) {
		super();
		this.date = date;
		this.description = description;
		this.originAccountName = originAccountName;
		this.destAccountName = destAccountName;
		this.value = value;
	}
	
	public boolean isNegativeValue() {
		return value.compareTo(BigDecimal.ZERO) < 0;
	}

}
