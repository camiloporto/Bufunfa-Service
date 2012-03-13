package br.com.bufunfa.finance.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;

@Configurable
public class AccountTree {
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	private AccountTreeNode rootNode;
	private AccountSystem accountSystem;
	
	public AccountTree(AccountSystem accountSystem, Account rootAccount) {
		this.rootNode = createNode(null, rootAccount);
		this.accountSystem = accountSystem;
	}
	

	public AccountTreeNode getRootNode() {
		return rootNode;
	}
	
	private AccountTreeNode createNode(Account parent, Account account) {
		return new AccountTreeNode(null, account);
	}
	
	public AccountTreeNode getAssetNode() {
		Account asset = accountSystemService.findAssetAccount(accountSystem);
		return createNode(getRootNode().getAccount(), asset);
	}


	public AccountTreeNode getLiabilityNode() {
		Account liability = accountSystemService.findLiabilityAccount(accountSystem);
		return createNode(getRootNode().getAccount(), liability);
	}


	public AccountTreeNode getIncomeNode() {
		Account income = accountSystemService.findIncomeAccount(accountSystem);
		return createNode(getRootNode().getAccount(), income);
	}


	public AccountTreeNode getOutcomeNode() {
		Account outcome = accountSystemService.findOutcomeAccount(accountSystem);
		return createNode(getRootNode().getAccount(), outcome);
	}

}
