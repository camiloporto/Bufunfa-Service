package br.com.bufunfa.finance.account.controller;

import java.util.Locale;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.roo.addon.serializable.RooSerializable;

import br.com.bufunfa.finance.account.i18n.AccountMessageSource;
import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountSystemService;

@RooSerializable
public class AccountTreeUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AccountSystemService accountSystemService;
	
	private AccountSystem accountSystem;
	
	private AccountTreeItemUI rootItem;
	
	private TreeNode rootNode = null;
	
	private AccountTreeItemUI selectedItem = null;
	
	private AccountTreeItemUI editingItem = new AccountTreeItemUI();
	
	private AccountTreeItemUI selectedItemParent = null;
	
	private AccountMessageSource messageSource;
	
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
		this.accountSystem = accountSystem;
		rootNode = new DefaultTreeNode("root", null);
		this.rootItem = new AccountTreeItemUI(rootAccount);
		this.rootItem.setNode(rootNode);
	}
	
	public void init() {
		/*
		 * Retrieve root Account Items (Asset, Liability, Income, Outcome
		 */
		AccountTreeItemUI assetItem = getAssetAccountTreeItem();
		assetItem.setRootAccount(true);
		
		AccountTreeItemUI liabilityItem = getLiabilityAccountTreeItem();
		liabilityItem.setRootAccount(true);
		
		AccountTreeItemUI incomeItem = getIncomeAccountTreeItem();
		incomeItem.setRootAccount(true);
		
		AccountTreeItemUI outcomeItem = getOutcomeAccountTreeItem();
		outcomeItem.setRootAccount(true);
		
		/*
		 * Configure i18nized root account names
		 */
		String assetName = getRootAccountsName(assetItem.getAccount().getName());
		String liabilityName = getRootAccountsName(liabilityItem.getAccount().getName());
		String incomeName = getRootAccountsName(incomeItem.getAccount().getName());
		String outcomeName = getRootAccountsName(outcomeItem.getAccount().getName());
		
		assetItem.setI18nAccountName(assetName);
		liabilityItem.setI18nAccountName(liabilityName);
		incomeItem.setI18nAccountName(incomeName);
		outcomeItem.setI18nAccountName(outcomeName);
		
		/*
		 * Creates initial Primefaces Tree on WUI
		 */
		
		TreeNode assetNode = new DefaultTreeNode(assetItem, rootNode);
		TreeNode liabilityNode = new DefaultTreeNode(liabilityItem, rootNode);
		TreeNode incomeNode = new DefaultTreeNode(incomeItem, rootNode);
		TreeNode outcomeNode = new DefaultTreeNode(outcomeItem, rootNode);
		
		assetItem.setNode(assetNode);
		liabilityItem.setNode(liabilityNode);
		incomeItem.setNode(incomeNode);
		outcomeItem.setNode(outcomeNode);
		
		/*
		 * Loads root account's children
		 */
		loadNodeChildren(assetItem);
		loadNodeChildren(liabilityItem);
		loadNodeChildren(incomeItem);
		loadNodeChildren(outcomeItem);
	}
	
	private void loadNodeChildren(AccountTreeItemUI parent) {
		Set<AccountTreeItemUI> children = parent.getChildren();
		for (AccountTreeItemUI childItem : children) {
			TreeNode childNode =  new DefaultTreeNode(childItem, parent.getNode());
			childItem.setNode(childNode);
			loadNodeChildren(childItem);
		}
	}
	
	String getRootAccountsName(String accountNameKey) {
		//FIXME colocar o Locale em um LocaleController e pegar o locale selecionado dele...
		String name = messageSource.getMessage(
				accountNameKey, 
				null, new Locale("pt", "BR"));
		return name;
	}
	
	AccountTreeItemUI getAssetAccountTreeItem() {
		Account asset = accountSystemService.findAssetAccount(accountSystem);
		return createTreeItem(asset);
	}
	
	AccountTreeItemUI getLiabilityAccountTreeItem() {
		Account liability = accountSystemService.findLiabilityAccount(accountSystem);
		return createTreeItem(liability);
	}
	
	AccountTreeItemUI getIncomeAccountTreeItem() {
		Account income = accountSystemService.findIncomeAccount(accountSystem);
		return createTreeItem(income);
	}
	
	AccountTreeItemUI getOutcomeAccountTreeItem() {
		Account outcome = accountSystemService.findOutcomeAccount(accountSystem);
		return createTreeItem(outcome);
	}
	
	//FIXME colocar esse metodo em uma fabrica que tenha todas as dependencias do Item para construi-lo
	AccountTreeItemUI createTreeItem(Account account) {
		AccountTreeItemUI item = new AccountTreeItemUI(account);
		item.setAccountSystemService(accountSystemService);
		return item;
	}

	public void setMessageSource(AccountMessageSource messageSource) {
		this.messageSource = messageSource;
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
			//FIXME REMOVER DO BANCO
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
		AccountTreeItemUI childItem = createTreeItem(saved);
		TreeNode child =  new DefaultTreeNode(childItem, getSelectedItemParent().getNode());
		childItem.setNode(child);
		
		//limpa o formulario
		this.editingItem = createTreeItem(new Account());
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
		this.editingItem = createTreeItem(new Account());
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
		AccountTreeItemUI item = getSelectedItem();
		Account toUpdate = item.getAccount();
		accountSystemService.updateAccount(toUpdate);
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
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(ctx != null) {
			ctx.addMessage(null, fmsg);
		}
	}


}
