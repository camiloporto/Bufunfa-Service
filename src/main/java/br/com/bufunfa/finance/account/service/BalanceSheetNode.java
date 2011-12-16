package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.modelo.Account;

@RooJavaBean
@Configurable
public class BalanceSheetNode {
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	@Autowired
	private AccountReportService accountReportService;
	
	private Long id;
	private Long fatherId;
	private String name;
	private BigDecimal balance;
	private Date date;
	private Collection<BalanceSheetNode> children = new ArrayList<BalanceSheetNode>();
	
	public BalanceSheetNode(Account a, BigDecimal balance, Date date) {
		this.name = a.getName();
		this.id = a.getId();
		this.fatherId = a.getFatherId();
		this.balance = balance;
		this.date = date;
	}

	public void addChild(Account child, BigDecimal balance, Date date) {
		BalanceSheetNode node = new BalanceSheetNode(child, balance, date);
		children.add(node);
	}

	public BalanceSheetNode getChildByName(String assetName) {
		lazyLoadNodeChildren();
		for (BalanceSheetNode node : this.children) {
			if(assetName.equals(node.getName())) {
				return node;
			}
		}
		return null;
	}
	
	public Collection<BalanceSheetNode> getChildren() {
		lazyLoadNodeChildren();
		return children;
	}
	
	private Collection<BalanceSheetNode> loadNodeChildren() {
		List<Account> accounts = accountSystemService.findAccountByFatherId(getId());
		Collection<BalanceSheetNode> nodes = new ArrayList<BalanceSheetNode>();
		for (Account a : accounts) {
			BigDecimal accountBalance = accountReportService.getAccountBalance(
					a, date);
			nodes.add(new BalanceSheetNode(
					a, 
					accountBalance,
					date));
		}
		return nodes;
	}
	
	private void lazyLoadNodeChildren() {
		if(children.isEmpty()) {
			children = loadNodeChildren();
		}
	}

	public void loadChildren() {
		lazyLoadNodeChildren();
	}

}
