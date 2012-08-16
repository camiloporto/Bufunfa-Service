package br.com.bufunfa.finance.account.controller;

import java.util.Calendar;
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
	
	private final Date FIRST_DAY_OF_MONTH;
	private final Date LAST_DAY_OF_MONTH;
	
	
	private Account account = new Account();
	private Date beginDate;
	private Date endDate;
	private AccountExtract accountExtract = null;
	
	private UIExtractTable uiExtractTable = null;
	

	@Autowired
	private AccountReportService reportService;
	
	public AccountExtractController() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		FIRST_DAY_OF_MONTH = c.getTime();
		
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.HOUR, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		LAST_DAY_OF_MONTH = c1.getTime();
		
	}
	
	
	public void updateExtract() {
		configureDefaultBeginDateIfNull();
		configureDefaultEndDateIfNull();
		AccountExtract extract = reportService.getAccountExtract(getAccount(), getBeginDate(), getEndDate());
		setUiExtractTable(new UIExtractTable(extract));
		setAccountExtract(extract);
	}

	private void configureDefaultBeginDateIfNull() {
		if(this.beginDate == null) {
			setBeginDate(FIRST_DAY_OF_MONTH);
		}
	}
	
	private void configureDefaultEndDateIfNull() {
		if(this.endDate == null) {
			setEndDate(LAST_DAY_OF_MONTH);
		}
	}

}
