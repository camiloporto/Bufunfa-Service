// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.service;

import br.com.bufunfa.finance.account.service.BalanceSheetNode;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Collection;

privileged aspect BalanceSheetNode_Roo_JavaBean {
    
    public Long BalanceSheetNode.getId() {
        return this.id;
    }
    
    public void BalanceSheetNode.setId(Long id) {
        this.id = id;
    }
    
    public Long BalanceSheetNode.getFatherId() {
        return this.fatherId;
    }
    
    public void BalanceSheetNode.setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }
    
    public String BalanceSheetNode.getName() {
        return this.name;
    }
    
    public void BalanceSheetNode.setName(String name) {
        this.name = name;
    }
    
    public BigDecimal BalanceSheetNode.getBalance() {
        return this.balance;
    }
    
    public void BalanceSheetNode.setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public Collection<BalanceSheetNode> BalanceSheetNode.getChildren() {
        return this.children;
    }
    
    public void BalanceSheetNode.setChildren(Collection<BalanceSheetNode> children) {
        this.children = children;
    }
    
}
