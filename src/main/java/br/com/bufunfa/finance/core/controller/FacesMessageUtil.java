package br.com.bufunfa.finance.core.controller;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class FacesMessageUtil {
	
	public static void addFacesMessage(String msg, Severity severity) {
		FacesMessage fmsg = new FacesMessage(severity, msg, msg);
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx != null) {
			ctx.addMessage(null, fmsg);
		}
	}
	
	public static void addExceptionMessagesToFacesContext(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : violations) {
			addFacesMessage(constraintViolation.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

}
