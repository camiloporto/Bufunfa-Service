package br.com.bufunfa.finance.account.service.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.bufunfa.finance.user.service.validation.UserParameters;

public class ServiceValidator {
	
	private Validator validator;
	
	//FIXME mudar para injecao de dependencia com Spring.
	public ServiceValidator() {
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		this.validator = f.getValidator();
	}
	
	public void validate(TransactionQueryParameters tp,
			Class<?>... class1) {
		Set<ConstraintViolation<TransactionQueryParameters>> violations = validator.validate(tp, class1);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	public void validate(TransactionParameters tp,
			Class<?>... class1) {
		Set<ConstraintViolation<TransactionParameters>> violations = validator.validate(tp, class1);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	public void validate(AccountReportParameters p,
			Class<?>... class1) {
		Set<ConstraintViolation<AccountReportParameters>> violations = validator.validate(p, class1);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	public void validate(UserParameters p,
			Class<?>... class1) {
		Set<ConstraintViolation<UserParameters>> violations = validator.validate(p, class1);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

}
