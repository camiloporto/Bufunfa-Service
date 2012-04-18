package br.com.bufunfa.finance.account.modelo;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooSerializable
public class Transaction {
	
	@OneToOne(cascade=CascadeType.ALL)
	private AccountEntry originAccountEntry;
	
	@OneToOne(cascade=CascadeType.ALL)
	private AccountEntry destAccountEntry;

}
