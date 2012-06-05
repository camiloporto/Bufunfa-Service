package br.com.bufunfa.finance.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.i18n.AccountMessageSource;
import br.com.bufunfa.finance.account.i18n.TransactionMessageSource;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.TransactionService;
import br.com.bufunfa.finance.core.controller.FacesMessageUtil;

@Controller
@RooSerializable
@RooJavaBean
@Scope("session")
public class TransactionController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8907619774726459159L;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountMessageSource messageSource;
	
	@Autowired
	private AccountController accountController;
	
	private TransactionUI currentTransaction = new TransactionUI();
	
	private List<TransactionUI> transactionList;// = new ArrayList<TransactionUI>();
	
	private AccountSystem getLoggedAccountSystem() {
		return accountController.getLoggedAccountSystem();
	}
	
	public void saveTransaction() {
		Transaction saved = transactionService.saveNewTransaction(
				getLoggedAccountSystem().getId(),
				currentTransaction.getFromAccount().getId(),
				currentTransaction.getToAccount().getId(), 
				currentTransaction.getDate(), 
				currentTransaction.getValue(), 
				currentTransaction.getComment());
		if(saved != null) {
			updateTransactionList();
			clearForm();
			String message = messageSource.getMessage(
					TransactionMessageSource.TRANSACTION_ADDED_SUCCESSFULLY, 
					null, new Locale("pt", "BR"));
			FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
		}
	}
	
	public void updateTransaction() {
		Transaction updated = transactionService.updateTransaction(
				getCurrentTransaction().getId(), 
				getCurrentTransaction().getFromAccount().getId(), 
				getCurrentTransaction().getToAccount().getId(), 
				getCurrentTransaction().getDate(), 
				getCurrentTransaction().getValue(), 
				getCurrentTransaction().getComment());
		if(updated != null) {
			updateTransactionList();
			clearForm();
			String message = messageSource.getMessage(
					TransactionMessageSource.TRANSACTION_UPDATED_SUCCESSFULLY, 
					null, new Locale("pt", "BR"));
			FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
		}
	}
	
	public void deleteTransaction() {
		transactionService.deleteTransaction(currentTransaction.getId());
		updateTransactionList();
		currentTransaction = new TransactionUI();
		String message = messageSource.getMessage(
				TransactionMessageSource.TRANSACTION_DELETED_SUCCESSFULLY, 
				null, new Locale("pt", "BR"));
		FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
	}


	void clearForm() {
		currentTransaction = new TransactionUI();
	}
	
	void updateTransactionList() {
		List<Transaction> list = transactionService.findAllTransactions(getLoggedAccountSystem().getId());
		List<TransactionUI> result = new ArrayList<TransactionUI>(list.size());
		for (Transaction t : list) {
			result.add(new TransactionUI(t));
		}
		setTransactionList(result);
	}

	public List<TransactionUI> getAllTransaction() {
		if(transactionList == null) {
			updateTransactionList();
		}
		return transactionList;
	}

}
