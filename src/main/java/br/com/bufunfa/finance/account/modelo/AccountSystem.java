package br.com.bufunfa.finance.account.modelo;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooJpaEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooSerializable
public class AccountSystem {

    @NotNull(message="{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}")
    @Size(min=1, message="{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_NAME.required}")
    private String name;

    @NotNull
    private long rootAccountId;
    
    @NotNull(message="{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}")
    @Size(min=1, message="{br.com.bufunfa.finance.service.AccountSystemService.ACCOUNT_SYSTEM_USER.required}")
    private String userId;
    
    @PrePersist
    private void trimStrings() {
    	if(name != null) {
    		name = name.trim();
    	}
    	if(userId != null) {
    		userId = userId.trim();
    	}
    }
    
}
