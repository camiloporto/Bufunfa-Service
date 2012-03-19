package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.util.DateUtils;
import br.com.bufunfa.finance.account.service.util.SpringRootTestsConfiguration;
import br.com.bufunfa.finance.core.ui.jsf.util.TestDataGenerator;
import br.com.bufunfa.finance.user.modelo.User;

public class TransactionControllerTest extends SpringRootTestsConfiguration {
	
	private static final String ASSET_ACCOUNT_NAME = "ativos";
	
	private static final String LIABILITY_ACCOUNT_NAME = "passivos";

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private AccountControllerHelper accountControllerHelper;
	
	private User sampleUser;
	
	@Test
	public void testAddNewTransaction_shuoldSuccess() {
		
		sampleUser = accountControllerHelper.generateAndSaveSampleUser();
		accountControllerHelper.loginSampleUser(sampleUser);

		Account from = accountControllerHelper.findAccountByName(ASSET_ACCOUNT_NAME);
		Account to = accountControllerHelper.findAccountByName(LIABILITY_ACCOUNT_NAME);;
		Date date = DateUtils.getDate(2012, Calendar.JANUARY, 10).getTime();
		BigDecimal value = new BigDecimal("100.00");
		String comment = TestDataGenerator.generateRandomString();
		
		transactionController.setFromAccount(from);
		transactionController.setToAccount(to);
		transactionController.setDate(date);
		transactionController.setValue(value);
		transactionController.setComment(comment);
		
		transactionController.saveTransaction();
		
		String msg = "transaction was not saved";
		assertThatTransactionExist(
				msg,
				from,
				to,
				date,
				value,
				comment
				);
		
		msg = "transaction form was not cleaned";
		assertThatFormIsCleaned(msg);
		
	}
	
	@Test
	public void testUpdateTransaction_shouldSuccess() {
		
		sampleUser = accountControllerHelper.generateAndSaveSampleUser();
		accountControllerHelper.loginSampleUser(sampleUser);

		Account from = accountControllerHelper.findAccountByName(ASSET_ACCOUNT_NAME);
		Account to = accountControllerHelper.findAccountByName(LIABILITY_ACCOUNT_NAME);;
		Date date = DateUtils.getDate(2012, Calendar.JANUARY, 10).getTime();
		BigDecimal value = new BigDecimal("100.00");
		String comment = TestDataGenerator.generateRandomString();
		
		transactionController.setFromAccount(from);
		transactionController.setToAccount(to);
		transactionController.setDate(date);
		transactionController.setValue(value);
		transactionController.setComment(comment);
		
		transactionController.saveTransaction();
		
		
		Transaction saved = findTransactionByComment(comment);
		
		transactionController.setEditTransactionId(saved.getId());
		from = accountControllerHelper.findAccountByName(LIABILITY_ACCOUNT_NAME);;
		to = accountControllerHelper.findAccountByName(ASSET_ACCOUNT_NAME);
		date = DateUtils.getDate(2012, Calendar.FEBRUARY, 10).getTime();
		value = new BigDecimal("50.75");
		comment = TestDataGenerator.generateRandomString();
		
		transactionController.setFromAccount(from);
		transactionController.setToAccount(to);
		transactionController.setDate(date);
		transactionController.setValue(value);
		transactionController.setComment(comment);
		
		transactionController.updateTransaction();
		
		
		String msg = "transaction was not updated";
		assertThatTransactionExist(
				msg,
				from,
				to,
				date,
				value,
				comment
				);
		
		msg = "transaction form was not cleaned";
		assertThatFormIsCleaned(msg);
		
	}

	
	private Transaction findTransactionByComment(String comment) {
		List<Transaction> transactions = transactionController.getAllTransaction();
		for (Transaction t : transactions) {
			if(comment.equals(t.getOriginAccountEntry().getComment())) {
				return t;
			}
		}
		return null;
	}

	private void assertThatFormIsCleaned(String msg) {
		Account from = transactionController.getFromAccount();
		Assert.assertTrue(msg + ": fromAccount.id", from.getId() == null);
		Assert.assertTrue(msg + ": fromAccount.name", from.getName() == null);
		
		Account to = transactionController.getToAccount();
		Assert.assertTrue(msg + ": toAccount.id", to.getId() == null);
		Assert.assertTrue(msg + ": toAccount.name", to.getName() == null);
		
		Assert.assertTrue(msg + ": date", transactionController.getDate() == null);
		Assert.assertTrue(msg + ": value", transactionController.getValue() == null);
		Assert.assertTrue(msg + ": comment", transactionController.getComment() == null);
	}

	private void assertThatTransactionExist(String msg, Account from,
			Account to, Date date, BigDecimal value, String comment) {
		boolean found = false;
		List<Transaction> transactions = transactionController.getAllTransaction();
		for (Transaction t : transactions) {
			if(comment.equals(t.getOriginAccountEntry().getComment())) {
				found = true;
				break;
			}
		}
		
		Assert.assertTrue(msg, found);
	}
	
}
