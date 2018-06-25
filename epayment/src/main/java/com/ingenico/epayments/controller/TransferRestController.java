package com.ingenico.epayments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.epayments.exception.CustomException;

import com.ingenico.epayments.model.Transaction;
import com.ingenico.epayments.service.TransferService;

/**
 * 
 * @author Devoteam
 *
 */
@RestController
@RequestMapping("/transfer")
public class TransferRestController {

	private static final Logger logger = LoggerFactory.getLogger(TransferRestController.class);

	@Autowired
	TransferService transferService;

	@RequestMapping(value = "/money", method = RequestMethod.POST)
	public ResponseEntity<?> transferMoney(@RequestBody Transaction transaction) {		
		boolean isMoneyTransferred = false;
		String responseMsg="";
		try {
			isMoneyTransferred = transferService.transferRequest(transaction);

			if (isMoneyTransferred)
				responseMsg = "Money Transferred Successfully!!" + transaction.toString();

		} catch (CustomException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			return new ResponseEntity<CustomException>(e, HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			return new ResponseEntity<Exception>(e, HttpStatus.CONFLICT);
		}
		logger.info(responseMsg);
		return new ResponseEntity<String>(responseMsg, HttpStatus.OK);

	}

}

// {"currencyCode": "EUR", "amount":50.0000, "fromAccountId":1011,
// "toAccountId":1002}
