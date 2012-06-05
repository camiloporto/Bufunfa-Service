// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.controller;

import br.com.bufunfa.finance.account.controller.AccountTreeItemUI;
import br.com.bufunfa.finance.account.controller.TransactionUI;
import br.com.bufunfa.finance.account.modelo.Account;
import java.math.BigDecimal;
import java.util.Date;

privileged aspect TransactionUI_Roo_JavaBean {
    
    public Account TransactionUI.getToAccount() {
        return this.toAccount;
    }
    
    public void TransactionUI.setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    
    public Account TransactionUI.getFromAccount() {
        return this.fromAccount;
    }
    
    public void TransactionUI.setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    
    public BigDecimal TransactionUI.getValue() {
        return this.value;
    }
    
    public void TransactionUI.setValue(BigDecimal value) {
        this.value = value;
    }
    
    public Date TransactionUI.getDate() {
        return this.date;
    }
    
    public void TransactionUI.setDate(Date date) {
        this.date = date;
    }
    
    public String TransactionUI.getComment() {
        return this.comment;
    }
    
    public void TransactionUI.setComment(String comment) {
        this.comment = comment;
    }
    
    public Long TransactionUI.getId() {
        return this.id;
    }
    
    public void TransactionUI.setId(Long id) {
        this.id = id;
    }
    
    public AccountTreeItemUI TransactionUI.getFromAccountItem() {
        return this.fromAccountItem;
    }
    
    public void TransactionUI.setFromAccountItem(AccountTreeItemUI fromAccountItem) {
        this.fromAccountItem = fromAccountItem;
    }
    
    public AccountTreeItemUI TransactionUI.getToAccountItem() {
        return this.toAccountItem;
    }
    
    public void TransactionUI.setToAccountItem(AccountTreeItemUI toAccountItem) {
        this.toAccountItem = toAccountItem;
    }
    
    public boolean TransactionUI.isEditMode() {
        return this.editMode;
    }
    
    public void TransactionUI.setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
}