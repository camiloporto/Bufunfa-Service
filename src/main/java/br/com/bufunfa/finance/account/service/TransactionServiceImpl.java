package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.service.validation.SaveTransactionValidationRules;
import br.com.bufunfa.finance.account.service.validation.TransactionParameterValidator;
import br.com.bufunfa.finance.account.service.validation.TransactionParameters;
import br.com.bufunfa.finance.account.service.validation.UpdateTransactionValidationRules;


public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountRepository accountRepository;
	
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Transaction saveNewTransaction(Long idOriginAccount,
			Long idDestAccount, Date date, BigDecimal value, String comment) {
		
		validateSaveTransaction(createParametersForSaveTransaction(idOriginAccount, idDestAccount, date, value, comment));
		
		AccountEntry origin = createAccountEntry(idOriginAccount, date, value.negate(), comment);
		AccountEntry dest = createAccountEntry(idDestAccount, date, value, comment);
		
		Transaction t = createTransaction(origin, dest);
		transactionRepository.save(t);
		
		return t;
	}
	
	public Transaction updateTransaction(
			Long idTransaction, Long idOriginAccount, Long idDestAccount, Date date,
			BigDecimal value, String comment) {
		
		validateUpdateTransaction(
				createParametersForUpdateTransaction(
						idTransaction, idOriginAccount, idDestAccount, date, value, comment
						));
		
		Transaction toUpdate = transactionRepository.findOne(idTransaction);
		AccountEntry originToUpdate = toUpdate.getOriginAccountEntry();
		AccountEntry destToUpdate = toUpdate.getDestAccountEntry();
		
		originToUpdate = updateAccountEntry(originToUpdate, idOriginAccount, date, value.negate(), comment);
		destToUpdate = updateAccountEntry(destToUpdate, idDestAccount, date, value, comment);
		
		toUpdate.setOriginAccountEntry(originToUpdate);
		toUpdate.setDestAccountEntry(destToUpdate);
		
		return transactionRepository.save(toUpdate);
		
	}
	
	public void deleteTransaction(Long id) {
		transactionRepository.delete(id);
	}
	
	
	private TransactionParameters createParametersForUpdateTransaction(
			Long idTransaction, Long idOriginAccount, Long idDestAccount,
			Date date, BigDecimal value, String comment) {
		TransactionParameters t = createParametersForSaveTransaction(idOriginAccount, idDestAccount, date, value, comment);
		t.setTransactionId(idTransaction);
		return t;
	}

	private AccountEntry updateAccountEntry(AccountEntry toUpdate,
			Long idAccount, Date date, BigDecimal value, String comment) {
		Account account = accountRepository.findOne(idAccount);
		toUpdate.setAccount(account);
		toUpdate.setComment(comment);
		toUpdate.setDate(date);
		toUpdate.setValue(value);
		
		return toUpdate;
	}

	private Transaction createTransaction(AccountEntry origin, AccountEntry dest) {
		Transaction t = new Transaction();
		t.setOriginAccountEntry(origin);
		t.setDestAccountEntry(dest);
		
		return t;
	}

	private AccountEntry createAccountEntry(Long idAccount,
			Date date, BigDecimal value, String comment) {
		Account account = accountRepository.findOne(idAccount);
		
		AccountEntry ae = new AccountEntry();
		ae.setAccount(account);
		ae.setComment(comment);
		ae.setDate(date);
		ae.setValue(value);
		
		return ae;
	}
	
	private TransactionParameters createParametersForSaveTransaction(Long idOriginAccount,
			Long idDestAccount, Date date, BigDecimal value, String comment) {
		
		TransactionParameters t = new TransactionParameters();
		t.setOriginAccountId(idOriginAccount);
		t.setDestAccountId(idDestAccount);
		t.setValue(value);
		t.setDate(date);
		
		return t;
	}
	
	private void validateSaveTransaction(TransactionParameters tp) {
		new TransactionParameterValidator().validate(tp, SaveTransactionValidationRules.class);
	}
	
	private void validateUpdateTransaction(TransactionParameters tp) {
		new TransactionParameterValidator().validate(tp, SaveTransactionValidationRules.class, UpdateTransactionValidationRules.class);
	}
	
}
