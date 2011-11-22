package br.com.bufunfa.finance.account.service.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TransactionQueryParameterValidator {
	
	private Validator validator;
	
	public TransactionQueryParameterValidator() {
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

}
