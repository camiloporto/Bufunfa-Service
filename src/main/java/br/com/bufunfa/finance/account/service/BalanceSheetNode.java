package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJavaBean
public class BalanceSheetNode {
	
	private Long id;
	private Long fatherId;
	private String name;
	private BigDecimal balance;
	private Collection<BalanceSheetNode> children = new ArrayList<BalanceSheetNode>();
	
	public BalanceSheetNode(Account a, BigDecimal balance) {
		this.name = a.getName();
		this.id = a.getId();
		this.fatherId = a.getFatherId();
		this.balance = balance;
	}

	public void addChild(Account child, BigDecimal balance) {
		BalanceSheetNode node = new BalanceSheetNode(child, balance);
		children.add(node);
	}

	public BalanceSheetNode getChildByName(String assetName) {
		for (BalanceSheetNode node : this.children) {
			if(assetName.equals(node.getName())) {
				return node;
			}
		}
		return null;
	}

}
