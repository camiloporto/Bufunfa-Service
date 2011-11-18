package br.com.bufunfa.finance.account.service.validation;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import br.com.bufunfa.finance.account.repository.AccountRepository;

@RooJavaBean
@Configurable
public class TransactionParameters {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT_ID.required}", 
			groups={SaveTransactionValidationRules.class})
	private Long originAccountId;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT_ID.required}", 
			groups={SaveTransactionValidationRules.class})
	private Long destAccountId;

	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.VALUE.required}", 
			groups={SaveTransactionValidationRules.class})
	@Min(value=0, message="{br.com.bufunfa.finance.service.TransactionService.VALUE.negative}", 
			groups={SaveTransactionValidationRules.class})
	private BigDecimal value;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.DATE.required}", 
			groups={SaveTransactionValidationRules.class})
	private Date date;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.TRANSACTION_ID.required}", 
			groups={UpdateTransactionValidationRules.class})
	private Long transactionId;
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT.notfound}", 
			groups={SaveTransactionValidationRules.class})
	private boolean isOriginAccountPersisted() {
		if(originAccountId != null) {
			return accountRepository.findOne(originAccountId) != null;
		}
		return false;
	}
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT.notfound}", 
			groups={SaveTransactionValidationRules.class})
	private boolean isDestinationAccountPersisted() {
		if(destAccountId != null) {
			return accountRepository.findOne(destAccountId) != null;
		}
		return false;
	}

}
