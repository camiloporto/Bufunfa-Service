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
public class AccountSystem {

    @NotNull
    private String name;

    @NotNull
    private long rootAccountId;
}
