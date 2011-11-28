package br.com.bufunfa.finance.account.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJavaBean
public class BalanceSheetNode {
	
	private String name;
	private Collection<BalanceSheetNode> children = new ArrayList<BalanceSheetNode>();
	
	public BalanceSheetNode(String name) {
		this.name = name;
	}

	public void addChild(Account asset) {
		BalanceSheetNode node = new BalanceSheetNode(asset.getName());
		children.add(node);
	}

}
