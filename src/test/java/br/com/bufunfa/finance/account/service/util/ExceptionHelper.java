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
	
	public void verifyTemplateErrorMessagesIsPresentAndI18nized(String testMsg, ConstraintViolationException e, String...expectedMessages) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (String expected : expectedMessages) {
			boolean foundExpectedMessage = false;
			boolean messageIsI18nized = false;
			for (Iterator iterator = violations.iterator(); iterator.hasNext();) {
				ConstraintViolation<?> v = (ConstraintViolation<?>) iterator
						.next();
				String msgTemplate = v.getMessageTemplate();
				String msgI18n = v.getMessage();
				if(msgTemplate.equals(expected)) {
					foundExpectedMessage = true;
					messageIsI18nized = !msgI18n.equals(msgTemplate);
					break;
				}
			}
			Assert.assertTrue(testMsg + " - expected template message not found: " + expected, foundExpectedMessage);
			Assert.assertTrue(testMsg + " - template message not I18nized: " + expected, messageIsI18nized);
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
