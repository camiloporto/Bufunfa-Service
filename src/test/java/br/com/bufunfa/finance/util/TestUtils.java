package br.com.bufunfa.finance.util;

public class TestUtils {
	
	public static String generateRandomString(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append((long)(Math.random() * size));
		}
		return sb.toString();
	}

}
