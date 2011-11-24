package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.service.util.DateUtils;

public class AccountExtractTest {
	
	@Test
	public void testCalculateBalance_shouldSumEntriesValue() {
		BigDecimal v1 = new BigDecimal("-10.00");
		AccountEntry a1 = createAccountEntry(v1);
		BigDecimal v2 = new BigDecimal("5.00");
		AccountEntry a2 = createAccountEntry(v2);
		List<AccountEntry> entries = new ArrayList<AccountEntry>();
		entries.add(a1);
		entries.add(a2);
		
		AccountExtract extract = new AccountExtract(entries);
		
		BigDecimal expectedBalance = new BigDecimal("-5.00");
		
		Assert.assertEquals(
				"expected balance did not match", 
				expectedBalance, 
				extract.getCurrentBalance());
		
	}

	
	private AccountEntry createAccountEntry(BigDecimal v) {
		AccountEntry a = new AccountEntry();
		a.setValue(v);
		a.setDate(DateUtils.getDate(2010, Calendar.JANUARY, 10).getTime());
		return a;
	}

}
