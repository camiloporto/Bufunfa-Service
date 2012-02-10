package br.com.bufunfa.finance.user.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.service.UserService;

@Controller
public class UserController {
	
	private User user = new User();
	
	@Autowired
	private UserService userService;

	public void saveNewUser() {
		userService.saveUser(getUser());
		System.out.println("UserController.saveNewUser() : " + getUser());
		addFacesMessage("usuario adicionado com sucesso", FacesMessage.SEVERITY_INFO);
		user = new User();
	}
	
	public String loginUser() {
		System.out.println("UserController.loginUser() " + getUser());
		return "account";
	}
	
	void addFacesMessage(String msg, Severity severity) {
		FacesMessage fmsg = new FacesMessage(severity, msg, msg);
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx != null) {
			ctx.addMessage(null, fmsg);
		}
	}

	public User getUser() {
		return user;
	}

}
