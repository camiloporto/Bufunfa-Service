// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.modelo;

import br.com.bufunfa.finance.account.modelo.AccountSystem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect AccountSystem_Roo_Jpa_Entity {
    
    declare @type: AccountSystem: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AccountSystem.id;
    
    @Version
    @Column(name = "version")
    private Integer AccountSystem.version;
    
    public Long AccountSystem.getId() {
        return this.id;
    }
    
    public void AccountSystem.setId(Long id) {
        this.id = id;
    }
    
    public Integer AccountSystem.getVersion() {
        return this.version;
    }
    
    public void AccountSystem.setVersion(Integer version) {
        this.version = version;
    }
    
}
