package br.com.bufunfa.finance.ui.account;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
public class AccountTreeUI {
	
	private TreeNode rootNode = new DefaultTreeNode("root", null);
	
	private AccountTreeItemUI selectedItem = null;
	
	private AccountTreeItemUI editingItem = new AccountTreeItemUI();
	
	private AccountTreeItemUI selectedItemParent = null;
	
	/**
	 * identifica se o item de arvore esta sendo
	 * adicionado ou atualizado (editado)
	 */
	private boolean editing = false;
	
	public AccountTreeUI() {
		//FIXME Contruir arvore de acordo com a estrutura de contas vinda do negocio
		
		AccountTreeItemUI ativos = new AccountTreeItemUI("Ativos", "conjunto de ativos");
		AccountTreeItemUI passivos = new AccountTreeItemUI("Passivos", "conjunto de passivos");
		AccountTreeItemUI receitas = new AccountTreeItemUI("Receitas", "conjunto de receitas");
		AccountTreeItemUI despesas = new AccountTreeItemUI("Despesas", "conjunto de despesas");
		
		AccountTreeItemUI salario = new AccountTreeItemUI("Salario Petro", "salario mensal da petrobras");
		
		TreeNode ativoNode = new DefaultTreeNode(ativos, rootNode);
		TreeNode passivoNode = new DefaultTreeNode(passivos, rootNode);
		TreeNode receitaNode = new DefaultTreeNode(receitas, rootNode);
		TreeNode despesaNode = new DefaultTreeNode(despesas, rootNode);
		
		TreeNode salarioNode = new DefaultTreeNode(salario, receitaNode);
		
		//associa os items aos nodos da arvore
		ativos.setNode(ativoNode);
		passivos.setNode(passivoNode);
		receitas.setNode(receitaNode);
		despesas.setNode(despesaNode);
		salario.setNode(salarioNode);
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
		
		//FIXME persistir atualizacoes
		AccountTreeItemUI childItem = new AccountTreeItemUI(
				getEditingItem().getAccountName(), 
				getEditingItem().getAccountDescription());
		
		TreeNode child =  new DefaultTreeNode(childItem, getSelectedItemParent().getNode());
		childItem.setNode(child);
		
		//limpa o formulario
		this.editingItem = new AccountTreeItemUI();
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
