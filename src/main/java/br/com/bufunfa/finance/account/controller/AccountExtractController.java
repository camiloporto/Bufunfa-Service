package br.com.bufunfa.finance.account.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.service.AccountExtract;
import br.com.bufunfa.finance.account.service.AccountReportService;

@RooSerializable
@RooJavaBean
@Controller
public class AccountExtractController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3564136337126502441L;
	private Account account = new Account();
	private Date beginDate;
	private Date endDate;
	private AccountExtract accountExtract = new AccountExtract();
	
	@Autowired
	private AccountReportService reportService;
	
	
	public void updateExtract() {
		AccountExtract extract = reportService.getAccountExtract(getAccount(), getBeginDate(), getEndDate());
		setAccountExtract(extract);
	}

}
