// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.modelo;

import java.lang.Integer;
import java.lang.Long;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Account_Roo_Jpa_Entity {
    
    declare @type: Account: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Account.id;
    
    @Version
    @Column(name = "version")
    private Integer Account.version;
    
    public Long Account.getId() {
        return this.id;
    }
    
    public void Account.setId(Long id) {
        this.id = id;
    }
    
    public Integer Account.getVersion() {
        return this.version;
    }
    
    public void Account.setVersion(Integer version) {
        this.version = version;
    }
    
}
