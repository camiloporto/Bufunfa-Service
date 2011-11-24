package br.com.bufunfa.finance.account.service.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {
	
	public static Calendar getDate(int year, int month, int day) {
		return new GregorianCalendar(year, month, day);
	}

}
