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
import br.com.bufunfa.finance.account.repository.AccountSystemRepository;
import br.com.bufunfa.finance.account.repository.TransactionRepository;

@RooJavaBean
@Configurable
public class TransactionParameters {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountSystemRepository accountSystemRepository;
	
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public void setAccountSystemRepository(
			AccountSystemRepository accountSystemRepository) {
		this.accountSystemRepository = accountSystemRepository;
	}
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.ACCOUNT_SYSTEM_ID.required}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class})
	private Long accountSystemId;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT_ID.required}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private Long originAccountId;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT_ID.required}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private Long destAccountId;

	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.VALUE.required}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	@Min(value=0, message="{br.com.bufunfa.finance.service.TransactionService.VALUE.negative}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private BigDecimal value;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.DATE.required}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private Date date;
	
	@NotNull(message="{br.com.bufunfa.finance.service.TransactionService.TRANSACTION_ID.required}", 
			groups={
				TransactionConstraintGroups.UpdateTransactionValidationRules.class, 
				TransactionConstraintGroups.DeleteTransactionValidationRules.class})
	private Long transactionId;
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.ORIGIN_ACCOUNT.notfound}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private boolean isOriginAccountPersisted() {
		if(originAccountId != null) {
			return accountRepository.findOne(originAccountId) != null;
		}
		return false;
	}
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.DEST_ACCOUNT.notfound}", 
			groups={TransactionConstraintGroups.SaveTransactionValidationRules.class,
					TransactionConstraintGroups.UpdateTransactionValidationRules.class})
	private boolean isDestinationAccountPersisted() {
		if(destAccountId != null) {
			return accountRepository.findOne(destAccountId) != null;
		}
		return false;
	}
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.TRANSACTION.notfound}", 
			groups={
				TransactionConstraintGroups.UpdateTransactionValidationRules.class, 
				TransactionConstraintGroups.DeleteTransactionValidationRules.class})
	private boolean isTransactionPersisted() {
		if(transactionId != null) {
			return transactionRepository.findOne(transactionId) != null;
		}
		return false;
	}
	
	@AssertTrue(message="{br.com.bufunfa.finance.service.TransactionService.ACCOUNT_SYSTEM.notfound}", 
			groups={
				TransactionConstraintGroups.SaveTransactionValidationRules.class})
	private boolean isAccountSystemPersisted() {
		if(accountSystemId != null) {
			return accountSystemRepository.findOne(accountSystemId) != null;
		}
		return false;
	}

}
