package com.ingenico.epayments;

import static junit.framework.TestCase.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ingenico.epayments.config.AppConfig;
import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;
import com.ingenico.epayments.service.AccountService;

/**
 * This test class is used to test Account Service business logic
 * 
 * @author Devoteam
 *
 */
public class TestAccountCreate extends EpaymentApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(TestAccountCreate.class);

	// This Random number used in to test case one to create an account and one to
	// check if Account already exist
	long randomNumber = (long) RandomUtils.nextInt(100000);

	@Autowired
	private AccountService accountService;

	
	/**
	 * This test case is used to create new Account
	 * 
	 * @throws CustomException
	 */
	@Test
	public void testCreateAccount() throws CustomException {
		Long accountID = randomNumber;
		BigDecimal balance = new BigDecimal(30).setScale(4, RoundingMode.HALF_EVEN);
		Account ac = new Account(accountID, "Test" + randomNumber, balance, "EUR");
		boolean result = accountService.createMonetaryAccount(ac);

		logger.info("testCreateAccount result " + result);
		assertTrue(result == true);

	}

	/**
	 * This test is used to check if Amount <30 EUR then Account can not be created
	 * 
	 * @throws CustomException
	 */
	@Test(expected = CustomException.class)
	public void testCreateAccountLessThanMinAmount() throws CustomException {

		Long accountID = RandomUtils.nextLong();
		BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Account ac = new Account(accountID, "Test2002", balance, "EUR");
		boolean result = accountService.createMonetaryAccount(ac);

		logger.info("testCreateAccountLessThanMinAmount result " + result);
	
	}

	/**
	 * This Test case is used to validate if the account is already created then
	 * thrown an error
	 * 
	 * @throws CustomException
	 */
	@Test(expected = CustomException.class)
	public void testAlreadyExistAccount() throws CustomException {

		Long accountID = randomNumber;
		BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Account ac = new Account(accountID, "Test" + randomNumber, balance, "EUR");
		boolean result = accountService.createMonetaryAccount(ac);

		logger.info("testAlreadyExistAccount result " + result);
		

	}

	@Test(expected = CustomException.class)
	public void testAccountHolderNameEmpty() throws CustomException {

		Long accountID = RandomUtils.nextLong();
		BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Account ac = new Account(accountID, "", balance, "EUR");
		boolean result = accountService.createMonetaryAccount(ac);
		logger.info("testAccountHolderNameEmpty result " + result);
		assertTrue(result == false);
	}
}
