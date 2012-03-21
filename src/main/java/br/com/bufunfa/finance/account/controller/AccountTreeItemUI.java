/**
 * 
 */
package br.com.bufunfa.finance.account.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.service.AccountSystemService;

/**
 * @author camilo
 *
 */
@RooJavaBean
@RooSerializable
public class AccountTreeItemUI implements Comparable<AccountTreeItemUI>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2519805200331802857L;
	
	@Autowired
	private AccountSystemService accountSystemService;
		
	private Account account;
	
	private SortedSet<AccountTreeItemUI> children = new TreeSet<AccountTreeItemUI>();
	
	private boolean rootAccount = false;//Asset, Liability, Income, Outcome = true
	
	private String i18nAccountName;
	
	private TreeNode node;
	
	private boolean actionPanelVisible = true;

	public AccountTreeItemUI(Account account) {
		this.account = account;
	}
	
	public AccountTreeItemUI() {
		this(new Account());
	}
	
	public boolean isLeaf() {
		return node != null ? node.isLeaf() : false;
	}
	
	//FIXME colocar esse metodo em uma fabrica que tenha todas as dependencias do Item para construi-lo
	AccountTreeItemUI createTreeItem(Account account) {
		AccountTreeItemUI item = new AccountTreeItemUI(account);
		item.setAccountSystemService(accountSystemService);
		return item;
	}
	
	public Set<AccountTreeItemUI> getChildren() {
		List<Account> accounts = accountSystemService.findAccountByFatherId(account.getId());
		for (Account account : accounts) {
			children.add(createTreeItem(account));
		}
		return children;
	}
	
	public Long getId() {
		return account.getId();
	}
	
	public String getAccountName() {
		if(isRootAccount() && this.i18nAccountName != null) {
			return this.i18nAccountName;
		}
		return account.getName();
	}
	
	public String getAccountDescription() {
		return account.getDescription();
	}
	
	public void setAccountName(String name) {
		account.setName(name);
	}
	
	public void setAccountDescription(String description) {
		account.setDescription(description);
	}
	
	/**
	 * Verifica se o item da arvore refere-se aos nos de
	 * primeiro nivel (ativo, passivo, receita, despesa)
	 * @return
	 */
	public boolean isFirstLevel() {
		if(node != null) {
			TreeNode parent = node.getParent();
			if(parent != null) {
				return parent.getParent() == null;
			}
		}
		return false;
	}
	
	public String getActionPanelVisibilityAttribute() {
		return isActionPanelVisible() ? "visibility:visible" : "visibility:hidden";
	}

	@Override
	public int compareTo(AccountTreeItemUI o) {
		return getAccountName().compareTo(o.getAccountName());
	}

}

