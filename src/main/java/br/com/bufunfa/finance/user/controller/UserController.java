package br.com.bufunfa.finance.user.controller;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
		try {
			userService.saveUser(getUser());
			addFacesMessage("usuario adicionado com sucesso", FacesMessage.SEVERITY_INFO);
			user = new User();
		} catch (ConstraintViolationException e) {
			//FIXME Refatorar esse codigo. Enrolar com aspecto para todos os controladores
			addExceptionMessagesToFacesContext(e);
		}
	}
	
	public String loginUser() {
		User u = userService.findUserByEmailAndPassword(
				getUser().getEmail(), 
				getUser().getPassword());
		if(u != null)
			return "account";
		return null;
	}
	
	void addFacesMessage(String msg, Severity severity) {
		FacesMessage fmsg = new FacesMessage(severity, msg, msg);
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx != null) {
			ctx.addMessage(null, fmsg);
		}
	}
	
	void addExceptionMessagesToFacesContext(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : violations) {
			addFacesMessage(constraintViolation.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public User getUser() {
		return user;
	}

}
