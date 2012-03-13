/**
 * 
 */
package br.com.bufunfa.finance.account.controller;

import org.primefaces.model.TreeNode;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

/**
 * @author camilo
 *
 */
//@RooToString
@RooJavaBean
@RooSerializable
public class AccountTreeItemUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2519805200331802857L;
	
	//TODO adicionar Id da conta aqui

	private Long id;
	
	private String accountName;
	
	private String accountDescription;
	
	private TreeNode node;
	
	private boolean actionPanelVisible = true;

	public AccountTreeItemUI(Long accountId, String nomeConta, String descricaoConta) {
		super();
		this.id = accountId;
		this.accountName = nomeConta;
		this.accountDescription = descricaoConta;
	}
	
	public AccountTreeItemUI() {
		
	}
	
	public boolean isLeaf() {
		return node != null ? node.isLeaf() : false;
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

}

