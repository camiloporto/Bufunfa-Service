// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.service;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import java.lang.Long;
import java.util.List;

privileged aspect AccountSystemService_Roo_Service {
    
    public abstract long AccountSystemService.countAllAccountSystems();    
    public abstract void AccountSystemService.deleteAccountSystem(AccountSystem accountSystem);    
    public abstract AccountSystem AccountSystemService.findAccountSystem(Long id);    
    public abstract List<AccountSystem> AccountSystemService.findAllAccountSystems();    
    public abstract List<AccountSystem> AccountSystemService.findAccountSystemEntries(int firstResult, int maxResults);    
    public abstract void AccountSystemService.saveAccountSystem(AccountSystem accountSystem);    
    public abstract AccountSystem AccountSystemService.updateAccountSystem(AccountSystem accountSystem);    
}
