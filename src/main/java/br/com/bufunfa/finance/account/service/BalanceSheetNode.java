package br.com.bufunfa.finance.account.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJavaBean
public class BalanceSheetNode {
	
	private Long id;
	private Long fatherId;
	private String name;
	private Collection<BalanceSheetNode> children = new ArrayList<BalanceSheetNode>();
	
	public BalanceSheetNode(Account a) {
		this.name = a.getName();
		this.id = a.getId();
		this.fatherId = a.getFatherId();
	}

	public void addChild(Account child) {
		BalanceSheetNode node = new BalanceSheetNode(child);
		children.add(node);
	}

}
