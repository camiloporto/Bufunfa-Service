package br.com.bufunfa.finance.core.ui.jsf.util;

public class TestDataGenerator {
	
	public static String generateValidEmail() {
		Long random = System.currentTimeMillis();
		return "email" + random + "@bufunfa.com"; 
	}

	public static String generateRandomString() {
		Long random = System.currentTimeMillis();
		return "string" + random;
	}

}
