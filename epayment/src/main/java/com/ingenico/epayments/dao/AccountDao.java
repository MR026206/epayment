package com.ingenico.epayments.dao;

import java.math.BigDecimal;



import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;


/**
 * 
 * @author Devoteam
 *
 */
public interface AccountDao {

	 int createAccount(Account account) throws CustomException;
	 
	 Account lockAccountForTransaction(long accountId) throws CustomException;
	 
	 Account getAccountInfo(long accountId) throws CustomException;
	 
	 int updateAccountBalance(long accountId, BigDecimal amount) throws CustomException;
	 
	 
	 
	
}
