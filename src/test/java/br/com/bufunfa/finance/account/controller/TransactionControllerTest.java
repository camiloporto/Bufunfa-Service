package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
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
		
		
		transactionController.getCurrentTransaction().setFromAccount(from);
		transactionController.getCurrentTransaction().setToAccount(to);
		transactionController.getCurrentTransaction().setDate(date);
		transactionController.getCurrentTransaction().setValue(value);
		transactionController.getCurrentTransaction().setComment(comment);
		
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
	public void testDeleteTransaction_shouldSuccess() {
		sampleUser = accountControllerHelper.generateAndSaveSampleUser();
		accountControllerHelper.loginSampleUser(sampleUser);
		TransactionUI saved = addSampleTransaction();
		
		transactionController.getCurrentTransaction().setId(saved.getId());
		transactionController.deleteTransaction();
		
		TransactionUI deleted = findTransactionByComment(saved.getComment());
		
		String msg = "transaction was not deleted";
		Assert.assertNull(msg, deleted);
		
		msg = "selected transaction id was not cleaned";
		Assert.assertNull(msg, transactionController.getCurrentTransaction().getId());
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
		
		
		TransactionUI saved = addSampleTransaction(from, to, date, value, comment);
		
		transactionController.getCurrentTransaction().setId(saved.getId());
		transactionController.getCurrentTransaction().setEditMode(true);
		from = accountControllerHelper.findAccountByName(LIABILITY_ACCOUNT_NAME);;
		to = accountControllerHelper.findAccountByName(ASSET_ACCOUNT_NAME);
		date = DateUtils.getDate(2012, Calendar.FEBRUARY, 10).getTime();
		value = new BigDecimal("50.75");
		comment = TestDataGenerator.generateRandomString();
		
		transactionController.getCurrentTransaction().setFromAccount(from);
		transactionController.getCurrentTransaction().setToAccount(to);
		transactionController.getCurrentTransaction().setDate(date);
		transactionController.getCurrentTransaction().setValue(value);
		transactionController.getCurrentTransaction().setComment(comment);
		
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
	

	private TransactionUI addSampleTransaction() {
		Account from = accountControllerHelper.findAccountByName(ASSET_ACCOUNT_NAME);
		Account to = accountControllerHelper.findAccountByName(LIABILITY_ACCOUNT_NAME);;
		Date date = DateUtils.getDate(2012, Calendar.JANUARY, 10).getTime();
		BigDecimal value = new BigDecimal("100.00");
		String comment = TestDataGenerator.generateRandomString();
		
		
		return addSampleTransaction(from, to, date, value, comment);
	}

	private TransactionUI addSampleTransaction(Account from, Account to, Date date, BigDecimal value, String comment) {
		transactionController.getCurrentTransaction().setFromAccount(from);
		transactionController.getCurrentTransaction().setToAccount(to);
		transactionController.getCurrentTransaction().setDate(date);
		transactionController.getCurrentTransaction().setValue(value);
		transactionController.getCurrentTransaction().setComment(comment);
		
		transactionController.saveTransaction();
		
		
		return findTransactionByComment(comment);
	}
	
	private TransactionUI findTransactionByComment(String comment) {
		List<TransactionUI> transactions = transactionController.getAllTransaction();
		for (TransactionUI t : transactions) {
			if(comment.equals(t.getComment())) {
				return t;
			}
		}
		return null;
	}

	private void assertThatFormIsCleaned(String msg) {
		Account from = transactionController.getCurrentTransaction().getFromAccount();
		Assert.assertTrue(msg + ": fromAccount.id", from.getId() == null);
		Assert.assertTrue(msg + ": fromAccount.name", from.getName() == null);
		
		Account to = transactionController.getCurrentTransaction().getToAccount();
		Assert.assertTrue(msg + ": toAccount.id", to.getId() == null);
		Assert.assertTrue(msg + ": toAccount.name", to.getName() == null);
		
		Assert.assertTrue(msg + ": date", transactionController.getCurrentTransaction().getDate() == null);
		Assert.assertTrue(msg + ": value", transactionController.getCurrentTransaction().getValue() == null);
		Assert.assertTrue(msg + ": comment", transactionController.getCurrentTransaction().getComment() == null);
	}

	private void assertThatTransactionExist(String msg, Account from,
			Account to, Date date, BigDecimal value, String comment) {
		boolean found = false;
		List<TransactionUI> transactions = transactionController.getAllTransaction();
		for (TransactionUI t : transactions) {
			if(comment.equals(t.getComment())) {
				found = true;
				break;
			}
		}
		
		Assert.assertTrue(msg, found);
	}
	
}
