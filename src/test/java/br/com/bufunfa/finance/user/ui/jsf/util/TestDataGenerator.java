package br.com.bufunfa.finance.user.ui.jsf.util;

public class TestDataGenerator {
	
	public static String generateValidEmail() {
		Long random = System.currentTimeMillis();
		return "email" + random + "@bufunfa.com"; 
	}

}
