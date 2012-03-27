package br.com.bufunfa.finance.account.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.TransactionService;

@Controller
@RooSerializable
@RooJavaBean
public class TransactionController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8907619774726459159L;

	@Autowired
	private TransactionService transactionService;
	
	private TransactionUI currentTransaction = new TransactionUI();
	
	public void saveTransaction() {
		Transaction saved = transactionService.saveNewTransaction(
				currentTransaction.getFromAccount().getId(),
				currentTransaction.getToAccount().getId(), 
				currentTransaction.getDate(), 
				currentTransaction.getValue(), 
				currentTransaction.getComment());
		if(saved != null) {
			clearForm();
			//FIXME adicionar mensagem faces i18nzada a WUI
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
			clearForm();
			//FIXME adicionar mensagem faces i18nzada a WUI
		}
	}
	
	public void deleteTransaction() {
		transactionService.deleteTransaction(currentTransaction.getId());
		currentTransaction = new TransactionUI();
		//FIXME adicionar mensagem faces i18nzada a WUI
	}


	void clearForm() {
		currentTransaction = new TransactionUI();
	}

	public List<TransactionUI> getAllTransaction() {
		List<Transaction> list = transactionService.findAllTransactions();
		List<TransactionUI> result = new ArrayList<TransactionUI>(list.size());
		for (Transaction t : list) {
			result.add(new TransactionUI(t));
		}
		return result;
	}

}
