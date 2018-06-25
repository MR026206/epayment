package com.ingenico.epayments.service;

import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Transaction;

/**
 * Transfer Service Interface
 * @author Devoteam
 *
 */
public interface TransferService {

	boolean transferRequest(Transaction transaction) throws CustomException;

}
