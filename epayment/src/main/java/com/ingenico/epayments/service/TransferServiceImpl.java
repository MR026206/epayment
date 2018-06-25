package com.ingenico.epayments.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ingenico.epayments.dao.AccountDao;
import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;
import com.ingenico.epayments.model.Transaction;

/**
 * This Service Class is used to handle Transaction i.e Money Transfer 
 * @author Devoteam
 *
 */
@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private AccountDao accountDao;

	
	/**
	 * This Method is used to transfer money from one Account to another account
	 * Validate To & From account validate Sufficient Balance in FROM Account
	 * in case of exception - roll-back DB transaction
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class  )
	public boolean transferRequest(Transaction transaction) throws CustomException {
		boolean result = false;

		Account toAccountObj = accountDao.lockAccountForTransaction(transaction.getToAccountId());

		// validate ToAccount
		if (toAccountObj == null) {
			throw new CustomException("Invalid To Account " + transaction.getToAccountId());
		}

		Account fromAccountObj = accountDao.lockAccountForTransaction(transaction.getFromAccountId());

		// validate FromAccount
		if (fromAccountObj == null) {
			throw new CustomException("Invalid From Account " + transaction.getFromAccountId());
		}
		// validate Balance FROM Account
		if (fromAccountObj.getBalance().compareTo(transaction.getAmount()) < 0) {
			throw new CustomException("Insufficient Balance in Source Account - " + fromAccountObj.getAccountId());
		}

		result = transferMoney(transaction, toAccountObj, fromAccountObj);

		return result;
	}

	/**
	 * This Method is used to Transfer money from one Account to another Account
	 * 
	 * 
	 * @param transaction
	 * @param toAccountObj
	 * @param fromAccountObj
	 * @return
	 * @throws CustomException
	 */
	private boolean transferMoney(Transaction transaction, Account toAccountObj, Account fromAccountObj)
			throws CustomException {
		boolean result = false;
		BigDecimal afterTransferToAccBalance;
		BigDecimal afterTransferFromAccBalance;
		// Transfer Money
		if (fromAccountObj.getBalance().compareTo(transaction.getAmount()) >= 0) {
			afterTransferFromAccBalance = fromAccountObj.getBalance().subtract(transaction.getAmount());
			afterTransferToAccBalance = toAccountObj.getBalance().add(transaction.getAmount());
			int toaccountUpdated = accountDao.updateAccountBalance(toAccountObj.getAccountId(), afterTransferToAccBalance);						
			int fromaccountupdated = accountDao.updateAccountBalance(fromAccountObj.getAccountId(), afterTransferFromAccBalance);
			result = (toaccountUpdated != 0 && fromaccountupdated != 0);
		}

		return result;
	}

}
