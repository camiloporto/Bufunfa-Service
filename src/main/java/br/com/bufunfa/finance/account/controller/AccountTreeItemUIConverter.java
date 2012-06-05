package br.com.bufunfa.finance.account.controller;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.web.jsf.FacesContextUtils;

public class AccountTreeItemUIConverter implements Converter, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3696086390845288650L;
	
	private AccountController accountController;
	
	public AccountTreeItemUIConverter() {
	}
	
	public void setAccountController(AccountController accountController) {
		this.accountController = accountController;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		String idStr = (String) value;
		Long id = Long.valueOf(idStr);
		return getAccountControllerFromSpringContext().findAccountItemById(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		AccountTreeItemUI item = (AccountTreeItemUI) value;
		return String.valueOf(item.getId());
	}
	
	public AccountController getAccountControllerFromSpringContext() {
		if(accountController == null) {
			setAccountController(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(AccountController.class));
		}
		return this.accountController;
	}

}
