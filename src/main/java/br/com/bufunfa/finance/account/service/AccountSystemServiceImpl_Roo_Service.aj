// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.service;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.repository.AccountSystemRepository;
import java.lang.Long;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AccountSystemServiceImpl_Roo_Service {
    
    declare @type: AccountSystemServiceImpl: @Service;
    
    declare @type: AccountSystemServiceImpl: @Transactional;
    
    @Autowired
    AccountSystemRepository AccountSystemServiceImpl.accountSystemRepository;
    
    @Autowired
    AccountRepository AccountSystemServiceImpl.accountRepository;
    
    public long AccountSystemServiceImpl.countAllAccountSystems() {
        return accountSystemRepository.count();
    }
    
    public void AccountSystemServiceImpl.deleteAccountSystem(AccountSystem accountSystem) {
        accountSystemRepository.delete(accountSystem);
    }
    
    public AccountSystem AccountSystemServiceImpl.findAccountSystem(Long id) {
        return accountSystemRepository.findOne(id);
    }
    
    public List<AccountSystem> AccountSystemServiceImpl.findAllAccountSystems() {
        return accountSystemRepository.findAll();
    }
    
    public List<AccountSystem> AccountSystemServiceImpl.findAccountSystemEntries(int firstResult, int maxResults) {
        return accountSystemRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public AccountSystem AccountSystemServiceImpl.updateAccountSystem(AccountSystem accountSystem) {
        return accountSystemRepository.save(accountSystem);
    }
    
    public long AccountSystemServiceImpl.countAllAccounts() {
        return accountRepository.count();
    }
    
    public void AccountSystemServiceImpl.deleteAccount(Account account) {
        accountRepository.delete(account);
    }
    
    public Account AccountSystemServiceImpl.findAccount(Long id) {
        return accountRepository.findOne(id);
    }
    
    public List<Account> AccountSystemServiceImpl.findAllAccounts() {
        return accountRepository.findAll();
    }
    
    public List<Account> AccountSystemServiceImpl.findAccountEntries(int firstResult, int maxResults) {
        return accountRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void AccountSystemServiceImpl.saveAccount(Account account) {
        accountRepository.save(account);
    }
    
    public Account AccountSystemServiceImpl.updateAccount(Account account) {
        return accountRepository.save(account);
    }
    
}
