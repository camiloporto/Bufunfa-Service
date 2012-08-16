package br.com.bufunfa.finance.account.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.service.AccountExtract;

public class UIExtractTable {
	
	//FIXME calcular o Saldo Anterior a data de inicio do extrato. para informar: Saldo Antes ... Saldo Depois
	private BigDecimal extractBalance;
	
	private List<UIExtractItem> extractItems = new ArrayList<UIExtractItem>();
	
	public UIExtractTable(AccountExtract accountExtract) {
		this.extractBalance = accountExtract.getCurrentBalance();
		extractItems = createExtractItems(accountExtract);
	}
	
	public BigDecimal getExtractBalance() {
		return extractBalance;
	}
	
	public List<UIExtractItem> getExtractItems() {
		return extractItems;
	}

	
	private List<UIExtractItem> createExtractItems(AccountExtract accountExtract) {
		List<UIExtractItem> items = new ArrayList<UIExtractItem>(accountExtract.getTransactionList().size());
		for (Transaction t : accountExtract.getTransactionList()) {
			UIExtractItem item = createUIExtractItem(accountExtract, t);
			items.add(item);
		}
		return items;
	}
	
	private UIExtractItem createUIExtractItem(AccountExtract extract, Transaction t) {
		return new UIExtractItem(
				t.getOriginAccountEntry().getDate(), 
				t.getOriginAccountEntry().getComment(), 
				t.getOriginAccountEntry().getAccount().getName(), 
				t.getDestAccountEntry().getAccount().getName(), 
				getExtractItemValue(extract, t));
	}


	private BigDecimal getExtractItemValue(AccountExtract extract, Transaction t) {
		Account extractAccount = extract.getAccount();
		Account src = t.getOriginAccountEntry().getAccount();
		if(extractAccount.getId().equals(src.getId())) {
			return t.getOriginAccountEntry().getValue();
		} else {
			return t.getDestAccountEntry().getValue();
		}
	}
	
}
