package br.com.bufunfa.finance.user.controller;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.controller.AccountController;
import br.com.bufunfa.finance.core.controller.FacesMessageUtil;
import br.com.bufunfa.finance.user.i18n.UserMessageSource;
import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.service.UserService;

//@Controller
//@Scope("session")
public class UserController {
	
	private User user = new User();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMessageSource messageSource;
	
	@Autowired
	private AccountController accountController;
	
	public void setAccountController(AccountController accountController) {
		this.accountController = accountController;
	}

	public void saveNewUser() {
		try {
			userService.saveUser(getUser());
			String message = messageSource.getMessage(
					UserMessageSource.USER_ADDED_SUCCESSFULLY, 
					null, new Locale("pt", "BR"));
			FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
			user = new User();
		} catch (ConstraintViolationException e) {
			//FIXME Refatorar esse codigo. Enrolar com aspecto para todos os controladores
			e.printStackTrace();
			FacesMessageUtil.addExceptionMessagesToFacesContext(e);
		}
	}
	
	public String loginUser() {
		User u = userService.findUserByEmailAndPassword(
				getUser().getEmail(), 
				getUser().getPassword());
		if(u != null) {
			//FIXME colocar dados do usuario na secao... Usar outra forma de seguranca: spring-security?
			accountController.loadAccountHierarchy(getUser());
			return "accountView?faces-redirect=true";
		} else {
			String message = messageSource.getMessage(
					UserMessageSource.USER_CREDENTIALS_FAILED, 
					null, new Locale("pt", "BR"));
			FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
	}
	
	public String logoutUser() {
		//FIXME retirar usuario da sessao.. Usar outra forma de seguranca - spring-security
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx != null) {
			ctx.getExternalContext().invalidateSession();
		}
		return "main?faces-redirect=true";
	}
	

	public User getUser() {
		return user;
	}

}
