package br.com.bufunfa.finance.account.service.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

public class ExceptionHelper {
	
	public void verifyTemplateErrorMessagesIn(String testMsg, ConstraintViolationException e, String...expectedMessages) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (String expected : expectedMessages) {
			boolean foundExpectedMessage = false;
			for (Iterator iterator = violations.iterator(); iterator.hasNext();) {
				ConstraintViolation<?> v = (ConstraintViolation<?>) iterator
						.next();
				if(v.getMessageTemplate().equals(expected)) {
					foundExpectedMessage = true;
					break;
				}
			}
			Assert.assertTrue(testMsg + " - expected template message not found: " + expected + "\nactual: " + getTemplateErrorMessages(e), 
					foundExpectedMessage);
		}
	}
	
	public void verifyInterpolatedErrorMessagesIn(String testMsg, ConstraintViolationException e, String...expectedMessages) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (String expected : expectedMessages) {
			boolean foundExpectedMessage = false;
			for (Iterator iterator = violations.iterator(); iterator.hasNext();) {
				ConstraintViolation<?> v = (ConstraintViolation<?>) iterator
						.next();
				if(v.getMessage().equals(expected)) {
					foundExpectedMessage = true;
					break;
				}
			}
			Assert.assertTrue(testMsg + " - expected interpolated message not found: " + expected, foundExpectedMessage);
		}
	}
	
	private String getTemplateErrorMessages(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		String[] msgs = new String[violations.size()];
		int i = 0;
		for (ConstraintViolation<?> c: violations) {
			msgs[i++] = c.getMessageTemplate();
		}
		return Arrays.deepToString(msgs);
	}
	
	private String getInterpolatedErrorMessages(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		String[] msgs = new String[violations.size()];
		int i = 0;
		for (ConstraintViolation<?> c: violations) {
			msgs[i++] = c.getMessage();
		}
		return Arrays.deepToString(msgs);
	}

}
