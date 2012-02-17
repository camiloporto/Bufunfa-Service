package br.com.bufunfa.finance.user.service.validation;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.configurable.RooConfigurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.repository.UserRepository;

@RooJavaBean
@RooSerializable
@RooConfigurable
public class UserParameters {
	
	@Autowired
	private UserRepository userRepository;

	@NotEmpty(message="{br.com.bufunfa.finance.user.service.UserService.EMAIL.required}",
			groups={UserConstraintGroups.UserServiceRules.class})
	private String email;
	
	@NotEmpty(message="{br.com.bufunfa.finance.user.service.UserService.PASSWORD.required}",
			groups={UserConstraintGroups.UserServiceRules.class})
	private String password;
	
	@AssertTrue(message="{br.com.bufunfa.finance.user.service.UserService.EMAIL.unique}", 
			groups={UserConstraintGroups.UserServiceRules.class})
	public boolean isEmailUnique() {
		if(email != null) {
			User u = userRepository.findByEmail(getEmail());
			return u == null;
		}
		return true;
	}
	
}
