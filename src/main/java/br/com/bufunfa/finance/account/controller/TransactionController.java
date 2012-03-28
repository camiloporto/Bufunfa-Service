package br.com.bufunfa.finance.account.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.TransactionService;
import br.com.bufunfa.finance.core.controller.FacesMessageUtil;

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
	
	private List<TransactionUI> transactionList = new ArrayList<TransactionUI>();
	
	public void saveTransaction() {
		Transaction saved = transactionService.saveNewTransaction(
				currentTransaction.getFromAccount().getId(),
				currentTransaction.getToAccount().getId(), 
				currentTransaction.getDate(), 
				currentTransaction.getValue(), 
				currentTransaction.getComment());
		if(saved != null) {
			updateTransactionList();
			clearForm();
			FacesMessageUtil.addFacesMessage("Transacao Salva", FacesMessage.SEVERITY_INFO);
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
			updateTransactionList();
			clearForm();
			FacesMessageUtil.addFacesMessage("Transacao Salva", FacesMessage.SEVERITY_INFO);
			//FIXME adicionar mensagem faces i18nzada a WUI
		}
	}
	
	public void deleteTransaction() {
		transactionService.deleteTransaction(currentTransaction.getId());
		updateTransactionList();
		currentTransaction = new TransactionUI();
		FacesMessageUtil.addFacesMessage("Transacao Removida", FacesMessage.SEVERITY_INFO);
		//FIXME adicionar mensagem faces i18nzada a WUI
	}


	void clearForm() {
		currentTransaction = new TransactionUI();
	}
	
	void updateTransactionList() {
		List<Transaction> list = transactionService.findAllTransactions();
		List<TransactionUI> result = new ArrayList<TransactionUI>(list.size());
		for (Transaction t : list) {
			result.add(new TransactionUI(t));
		}
		setTransactionList(result);
	}

	public List<TransactionUI> getAllTransaction() {
//		if(transactionList.isEmpty()) {
//			List<Transaction> list = transactionService.findAllTransactions();
//			List<TransactionUI> result = new ArrayList<TransactionUI>(list.size());
//			for (Transaction t : list) {
//				result.add(new TransactionUI(t));
//			}
//			return result;
//		}
		return transactionList;
	}

}
