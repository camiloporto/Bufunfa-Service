// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.modelo;

import br.com.bufunfa.finance.account.modelo.AccountEntry;

privileged aspect Transaction_Roo_JavaBean {
    
    public AccountEntry Transaction.getOriginAccountEntry() {
        return this.originAccountEntry;
    }
    
    public void Transaction.setOriginAccountEntry(AccountEntry originAccountEntry) {
        this.originAccountEntry = originAccountEntry;
    }
    
    public AccountEntry Transaction.getDestAccountEntry() {
        return this.destAccountEntry;
    }
    
    public void Transaction.setDestAccountEntry(AccountEntry destAccountEntry) {
        this.destAccountEntry = destAccountEntry;
    }
    
}
