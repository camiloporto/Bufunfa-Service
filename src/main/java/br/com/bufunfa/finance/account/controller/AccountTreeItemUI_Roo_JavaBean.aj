// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.controller;

import br.com.bufunfa.finance.account.controller.AccountTreeItemUI;
import br.com.bufunfa.finance.account.service.AccountTreeNode;
import org.primefaces.model.TreeNode;

privileged aspect AccountTreeItemUI_Roo_JavaBean {
    
    public AccountTreeNode AccountTreeItemUI.getAccountTreeNode() {
        return this.accountTreeNode;
    }
    
    public void AccountTreeItemUI.setAccountTreeNode(AccountTreeNode accountTreeNode) {
        this.accountTreeNode = accountTreeNode;
    }
    
    public Long AccountTreeItemUI.getId() {
        return this.id;
    }
    
    public void AccountTreeItemUI.setId(Long id) {
        this.id = id;
    }
    
    public String AccountTreeItemUI.getAccountName() {
        return this.accountName;
    }
    
    public void AccountTreeItemUI.setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String AccountTreeItemUI.getAccountDescription() {
        return this.accountDescription;
    }
    
    public void AccountTreeItemUI.setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }
    
    public TreeNode AccountTreeItemUI.getNode() {
        return this.node;
    }
    
    public void AccountTreeItemUI.setNode(TreeNode node) {
        this.node = node;
    }
    
    public boolean AccountTreeItemUI.isActionPanelVisible() {
        return this.actionPanelVisible;
    }
    
    public void AccountTreeItemUI.setActionPanelVisible(boolean actionPanelVisible) {
        this.actionPanelVisible = actionPanelVisible;
    }
    
}
