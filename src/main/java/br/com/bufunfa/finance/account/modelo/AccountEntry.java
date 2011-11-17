package br.com.bufunfa.finance.account.modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.ManyToOne;
import org.springframework.roo.addon.entity.RooJpaEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooSerializable
public class AccountEntry {

    private Date date;

    private BigDecimal value;

    private String comment;

    @ManyToOne
    private Account account;
}
