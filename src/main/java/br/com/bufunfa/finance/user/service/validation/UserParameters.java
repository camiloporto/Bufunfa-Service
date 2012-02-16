package br.com.bufunfa.finance.user.service.validation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooJavaBean
@RooSerializable
public class UserParameters {

	@NotEmpty(message="{br.com.bufunfa.finance.user.service.UserService.EMAIL.required}",
			groups={UserConstraintGroups.UserServiceRules.class})
	private String email;
	
	@NotEmpty(message="{br.com.bufunfa.finance.user.service.UserService.PASSWORD.required}",
			groups={UserConstraintGroups.UserServiceRules.class})
	private String password;
	
}
