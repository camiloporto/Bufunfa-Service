// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.controller;

import br.com.bufunfa.finance.account.controller.TransactionController;
import br.com.bufunfa.finance.account.controller.TransactionUI;
import br.com.bufunfa.finance.account.service.TransactionService;
import java.util.List;

privileged aspect TransactionController_Roo_JavaBean {
    
    public TransactionService TransactionController.getTransactionService() {
        return this.transactionService;
    }
    
    public void TransactionController.setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    public TransactionUI TransactionController.getCurrentTransaction() {
        return this.currentTransaction;
    }
    
    public void TransactionController.setCurrentTransaction(TransactionUI currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
    
    public List<TransactionUI> TransactionController.getTransactionList() {
        return this.transactionList;
    }
    
    public void TransactionController.setTransactionList(List<TransactionUI> transactionList) {
        this.transactionList = transactionList;
    }
    
}
