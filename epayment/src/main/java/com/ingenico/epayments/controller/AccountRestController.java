package com.ingenico.epayments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;
import com.ingenico.epayments.service.AccountService;


/**
 * This class is used to handle Account Request.
 * 
 * @author Devoteam
 *
 */
@RestController
@RequestMapping("/account")
public class AccountRestController {

	private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);

	
	@Autowired
	AccountService accountService;

	/**
	 * This method is used to create a new Account .
	 * @param ac
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestBody Account ac) {
		
		boolean isAccountCreated = false;
		String responseMsg = "";
		try {
			isAccountCreated = accountService.createMonetaryAccount(ac);

			if (isAccountCreated)
				responseMsg = "Account Created Successfully !!- " + ac.toString();
			
		} catch (CustomException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			return new ResponseEntity<CustomException>(e, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			return new ResponseEntity<Exception>(e, HttpStatus.CONFLICT);
		}

		logger.info(responseMsg);
		
		return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);

	}

}

// {"id": 1013, "name":"Mukesh", "balance":500.000, "currency":"EUR"}
