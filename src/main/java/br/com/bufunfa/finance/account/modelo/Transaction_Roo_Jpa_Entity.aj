// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.bufunfa.finance.account.modelo;

import br.com.bufunfa.finance.account.modelo.Transaction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Transaction_Roo_Jpa_Entity {
    
    declare @type: Transaction: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Transaction.id;
    
    @Version
    @Column(name = "version")
    private Integer Transaction.version;
    
    public Long Transaction.getId() {
        return this.id;
    }
    
    public void Transaction.setId(Long id) {
        this.id = id;
    }
    
    public Integer Transaction.getVersion() {
        return this.version;
    }
    
    public void Transaction.setVersion(Integer version) {
        this.version = version;
    }
    
}
