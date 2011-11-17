package br.com.bufunfa.finance.account.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountEntry;
import br.com.bufunfa.finance.account.modelo.Transaction;
import br.com.bufunfa.finance.account.repository.AccountRepository;
import br.com.bufunfa.finance.account.service.validation.TransactionParameterValidator;
import br.com.bufunfa.finance.account.service.validation.TransactionParameters;


public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountRepository accountRepository;
	
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Transaction saveNewTransaction(Long idOriginAccount,
			Long idDestAccount, Date date, BigDecimal value, String comment) {
		
		validate(createParametersForSaveTransaction(idOriginAccount, idDestAccount, date, value, comment));
		
		AccountEntry origin = createAccountEntry(idOriginAccount, date, value.negate(), comment);
		AccountEntry dest = createAccountEntry(idDestAccount, date, value, comment);
		
		Transaction t = createTransaction(origin, dest);
		transactionRepository.save(t);
		
		return t;
	}
	
	private Transaction createTransaction(AccountEntry origin, AccountEntry dest) {
		Transaction t = new Transaction();
		t.setOriginAccountEntry(origin);
		t.setDestAccountEntry(dest);
		
		return t;
	}

	private AccountEntry createAccountEntry(Long idOriginAccount,
			Date date, BigDecimal value, String comment) {
		Account account = accountRepository.findOne(idOriginAccount);
		
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
		
		return t;
	}
	
	private void validate(TransactionParameters tp) {
		new TransactionParameterValidator().validateSaveTransaction(tp);
	}
	
}
