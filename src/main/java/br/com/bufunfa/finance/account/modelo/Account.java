package br.com.bufunfa.finance.account.modelo;

import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooJpaEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooSerializable
public class Account {

	public final static String INCOME_NAME = "br.com.bufunfa.finance.modelo.account.INCOME_ACCOUNT_NAME";
	
	public final static String OUTCOME_NAME = "br.com.bufunfa.finance.modelo.account.OUTCOME_ACCOUNT_NAME";
	
    @NotNull
    private String name;

    private String description;

  //TODO deve ser obrigatorio, exceto a root conta que eh transparente para o usuario
    private Long fatherId;
}
