// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.service.validation;

import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.repository.TransactionRepository;
import java.lang.Long;
import java.math.BigDecimal;
import java.util.Date;

privileged aspect TransactionParameters_Roo_JavaBean {
    
    public AccountRepository TransactionParameters.getAccountRepository() {
        return this.accountRepository;
    }
    
    public TransactionRepository TransactionParameters.getTransactionRepository() {
        return this.transactionRepository;
    }
    
    public void TransactionParameters.setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public Long TransactionParameters.getOriginAccountId() {
        return this.originAccountId;
    }
    
    public void TransactionParameters.setOriginAccountId(Long originAccountId) {
        this.originAccountId = originAccountId;
    }
    
    public Long TransactionParameters.getDestAccountId() {
        return this.destAccountId;
    }
    
    public void TransactionParameters.setDestAccountId(Long destAccountId) {
        this.destAccountId = destAccountId;
    }
    
    public BigDecimal TransactionParameters.getValue() {
        return this.value;
    }
    
    public void TransactionParameters.setValue(BigDecimal value) {
        this.value = value;
    }
    
    public Date TransactionParameters.getDate() {
        return this.date;
    }
    
    public void TransactionParameters.setDate(Date date) {
        this.date = date;
    }
    
    public Long TransactionParameters.getTransactionId() {
        return this.transactionId;
    }
    
    public void TransactionParameters.setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    
}
