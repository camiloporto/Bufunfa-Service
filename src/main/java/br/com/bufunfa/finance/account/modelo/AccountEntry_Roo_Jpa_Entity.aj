// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.modelo;

import br.com.bufunfa.finance.account.modelo.AccountEntry;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect AccountEntry_Roo_Jpa_Entity {
    
    declare @type: AccountEntry: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AccountEntry.id;
    
    @Version
    @Column(name = "version")
    private Integer AccountEntry.version;
    
    public Long AccountEntry.getId() {
        return this.id;
    }
    
    public void AccountEntry.setId(Long id) {
        this.id = id;
    }
    
    public Integer AccountEntry.getVersion() {
        return this.version;
    }
    
    public void AccountEntry.setVersion(Integer version) {
        this.version = version;
    }
    
}
