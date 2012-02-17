package br.com.bufunfa.finance.user.controller;

import javax.faces.application.FacesMessage;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.core.controller.FacesMessageUtil;
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
			FacesMessageUtil.addFacesMessage("usuario adicionado com sucesso", FacesMessage.SEVERITY_INFO);
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
			return "account";
		} else {
			FacesMessageUtil.addFacesMessage("Usuário e senha inválidos", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		
	}
	

	public User getUser() {
		return user;
	}

}
