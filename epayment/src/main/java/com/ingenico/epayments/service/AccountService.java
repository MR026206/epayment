package com.ingenico.epayments.service;

import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;

/**
 * Account Service Interface 
 * @author Devoteam
 *
 */
public interface AccountService {

	boolean createMonetaryAccount(Account ac) throws CustomException ;
	
}
