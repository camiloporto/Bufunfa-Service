package br.com.bufunfa.finance.account.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.roo.addon.serializable.RooSerializable;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.account.service.AccountTree;
import br.com.bufunfa.finance.account.service.AccountTreeNode;

@RooSerializable
public class AccountTreeUI {
	
	private AccountTree accountTree;
	
	private AccountSystemService accountSystemService;
	
	private TreeNode rootNode = new DefaultTreeNode("root", null);
	
	private AccountTreeItemUI selectedItem = null;
	
	private AccountTreeItemUI editingItem = new AccountTreeItemUI();
	
	private AccountTreeItemUI selectedItemParent = null;
	
	
	
	/**
	 * identifica se o item de arvore esta sendo
	 * adicionado ou atualizado (editado)
	 */
	private boolean editing = false;
	
	public void setAccountSystemService(
			AccountSystemService accountSystemService) {
		this.accountSystemService = accountSystemService;
	}
	
	public AccountTreeUI(AccountSystem accountSystem, Account rootAccount) {
		accountTree = new AccountTree(accountSystem, rootAccount);
		AccountTreeNode asset = accountTree.getAssetNode();
		AccountTreeNode liability = accountTree.getLiabilityNode();
		AccountTreeNode income = accountTree.getIncomeNode();
		AccountTreeNode outcome = accountTree.getOutcomeNode();
		
		AccountTreeItemUI assetItem = new AccountTreeItemUI(
				asset.getAccount().getId(),
				asset.getAccount().getName(), 
				asset.getAccount().getName());
		
		AccountTreeItemUI liabilityItem = new AccountTreeItemUI(
				liability.getAccount().getId(),
				liability.getAccount().getName(), 
				liability.getAccount().getName());
		
		AccountTreeItemUI incomeItem = new AccountTreeItemUI(
				income.getAccount().getId(),
				income.getAccount().getName(), 
				income.getAccount().getName());
		
		AccountTreeItemUI outcomeItem = new AccountTreeItemUI(
				outcome.getAccount().getId(), 
				outcome.getAccount().getName(), 
				outcome.getAccount().getName());
		
		TreeNode assetNode = new DefaultTreeNode(assetItem, rootNode);
		TreeNode liabilityNode = new DefaultTreeNode(liabilityItem, rootNode);
		TreeNode incomeNode = new DefaultTreeNode(incomeItem, rootNode);
		TreeNode outcomeNode = new DefaultTreeNode(outcomeItem, rootNode);
		
		assetItem.setNode(assetNode);
		liabilityItem.setNode(liabilityNode);
		incomeItem.setNode(incomeNode);
		outcomeItem.setNode(outcomeNode);
	}

	public TreeNode getRootNode() {
		return rootNode;
	}
	
	public AccountTreeItemUI getSelectedItem() {
		return selectedItem;
	}
	
	public void setSelectedItem(AccountTreeItemUI selectedNode) {
		this.selectedItem = selectedNode;
	}
	
	public AccountTreeItemUI getSelectedItemParent() {
		return selectedItemParent;
	}
	
	public AccountTreeItemUI getEditingItem() {
		return editingItem;
	}
	
	public void setEditingItem(AccountTreeItemUI newItem) {
		this.editingItem = newItem;
	}
	
	public boolean isEditing() {
		return editing;
	}
	
	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
	//TODO Incrementar interface com recursos de usabilidade (msgs de status, icones para acoes, destaque de linhas, mostrar acoes apenas na linha destacada?? etc..)
	
	/**
	 * FIXME persistir atualizacoes
	 */
	public void deleteItem() {
		if(getSelectedItem() == null)
			return;
		
		TreeNode deleteCandidate = getSelectedItem().getNode();
		if(deleteCandidate.isLeaf()) {
			TreeNode parentNode = deleteCandidate.getParent();
			
			parentNode.getChildren().remove(deleteCandidate);
			deleteCandidate.setParent(null);
			addFacesMessage("Conta removida com sucesso", FacesMessage.SEVERITY_INFO);
			
		} else {
			//nao se pode remover no nao folha
		}
		
	}
	
	public void addItem() {
		if(getSelectedItem() == null)
			return;
		
		this.selectedItemParent = getSelectedItem();
		
		Account saved = saveAccount(selectedItemParent.getId());
		
		AccountTreeItemUI childItem = new AccountTreeItemUI(
				saved.getId(),
				saved.getName(), 
				saved.getDescription());
		
		TreeNode child =  new DefaultTreeNode(childItem, getSelectedItemParent().getNode());
		childItem.setNode(child);
		
		//limpa o formulario
		this.editingItem = new AccountTreeItemUI();
	}
	
	Account saveAccount(Long fatherId) {
		Account a = new Account();
		a.setDescription(getEditingItem().getAccountDescription());
		a.setName(getEditingItem().getAccountName());
		a.setFatherId(fatherId);
		
		accountSystemService.saveAccount(a);
		
		return a;
	}
	
	/**
	 * Mostra o formulario para preenchimento
	 * do novo item da arvore de contas
	 */
	public void showNewItemForm() {
		//limpa formulario a ser apresentado
		this.editingItem = new AccountTreeItemUI();
	}
	
	/**
	 * Mostra o formulario para edicao
	 * de item na arvore de contas
	 */
	public void showEditItemForm() {
		
		this.selectedItemParent = (AccountTreeItemUI) getSelectedItem().getNode().getParent().getData();
		
		//Preenche o formulario com os dados do item a ser editado
		this.editingItem = getSelectedItem();
		
	}
	
	public void updateItem() {
		//FIXME persistir as atualizacoes
	}
	
	public void saveItem() {
		if(isEditing())
			updateItem();
		if(!isEditing())
			addItem();
		
		addFacesMessage("Conta salva com sucesso", FacesMessage.SEVERITY_INFO);
	}
	
	/**
	 * Adiciona uma msg generica (nao atrelada a um componente)
	 * ao faces context
	 * @param msg a msg
	 * @param severity severidade da msg
	 */
	void addFacesMessage(String msg, Severity severity) {
		FacesMessage fmsg = new FacesMessage(severity, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
	}


}
