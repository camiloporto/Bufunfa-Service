// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.user.service.validation;

import br.com.bufunfa.finance.user.repository.UserRepository;
import br.com.bufunfa.finance.user.service.validation.UserParameters;

privileged aspect UserParameters_Roo_JavaBean {
    
    public UserRepository UserParameters.getUserRepository() {
        return this.userRepository;
    }
    
    public void UserParameters.setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String UserParameters.getEmail() {
        return this.email;
    }
    
    public void UserParameters.setEmail(String email) {
        this.email = email;
    }
    
    public String UserParameters.getPassword() {
        return this.password;
    }
    
    public void UserParameters.setPassword(String password) {
        this.password = password;
    }
    
}