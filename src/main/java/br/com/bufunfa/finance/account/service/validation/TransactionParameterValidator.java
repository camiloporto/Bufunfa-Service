package br.com.bufunfa.finance.account.service.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TransactionParameterValidator {

	private Validator validator;
	
	public TransactionParameterValidator() {
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		this.validator = f.getValidator();
	}
	
	public void validateSaveTransaction(TransactionParameters tp) {
		Set<ConstraintViolation<TransactionParameters>> violations = validator.validate(tp, SaveTransactionValidationRules.class);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
}
