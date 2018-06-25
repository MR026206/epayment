package com.ingenico.epayments;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ingenico.epayments.dao.AccountDao;
import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;
import com.ingenico.epayments.model.Transaction;
import com.ingenico.epayments.service.TransferService;

public class TestTransferMoeny extends EpaymentApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(TestTransferMoeny.class);

	@Autowired
	private TransferService transferService;

	@Autowired
	private AccountDao accountDao;

	/**
	 * Transfer 10 Euro from Test account - 3001 to another Test Account - 3005
	 * 
	 * @throws CustomException
	 */
	@Test
	public void testTransferMoenyCase1() throws CustomException {
		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3001;
		Long toAccount = (long) 3005;

		// before transfer - To/From Account Balance
		Account beforeTransferfromAcc = accountDao.getAccountInfo(fromAccount);
		Account beforeTransferToAcc = accountDao.getAccountInfo(toAccount);
		
		Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);

		// Transfer Money
		boolean result = transferService.transferRequest(transaction);

		// After transfer -  To/From Account Balance
		Account afterTransferfromAcc = accountDao.getAccountInfo(fromAccount);
		Account afterTransferToAcc = accountDao.getAccountInfo(toAccount);
		BigDecimal afterTransferFromAccountBalance = afterTransferfromAcc.getBalance();
		BigDecimal afterTransfertoAccountBalance = afterTransferToAcc.getBalance();

		System.out.println("Before"+beforeTransferfromAcc.getBalance().add(beforeTransferToAcc.getBalance()));
		System.out.println("After"+afterTransferFromAccountBalance.add(afterTransfertoAccountBalance));
		
		// test case if before and After balance is true && Money Transfer Result is
		// true
		assertTrue(beforeTransferfromAcc.getBalance().add(beforeTransferToAcc.getBalance())
				.compareTo(afterTransferFromAccountBalance.add(afterTransfertoAccountBalance)) == 0 && result);

	}

	/**
	 * Transfer 10 Euro from Test account - 3005 to another Test Account - 3001 This
	 * Test case is same as Test Case 1 - This test case added so that Account
	 * balance will never be zero :)
	 * 
	 * @throws CustomException
	 */
	public void testTransferMoenyCase2() throws CustomException {
		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3005;
		Long toAccount = (long) 3001;

		// before transfer To/From Account Balance
		Account beforeTransferfromAcc = accountDao.getAccountInfo(fromAccount);
		Account beforeTransferToAcc = accountDao.getAccountInfo(toAccount);
		
		Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);

		// Transfer Money
		boolean result = transferService.transferRequest(transaction);

		// After transfer To/From Account Balance
		Account afterTransferfromAcc = accountDao.getAccountInfo(fromAccount);
		Account afterTransferToAcc = accountDao.getAccountInfo(toAccount);
		BigDecimal afterTransferFromAccountBalance = afterTransferfromAcc.getBalance();
		BigDecimal afterTransfertoAccountBalance = afterTransferToAcc.getBalance();

		// test case if before and After balance is true && Money Transfer result is
		// true
		assertTrue(beforeTransferfromAcc.getBalance().add(beforeTransferToAcc.getBalance())
				.compareTo(afterTransferFromAccountBalance.add(afterTransfertoAccountBalance)) == 0 && result);

	}

	/**
	 * This test case is used to test - if the Account does not have money an system
	 * throw an Error. Test Account 3006 does not have balance i.e. 0.0000 EURO
	 * 
	 * @throws CustomException
	 */
	@Test(expected = Exception.class)
	public void testTransferMoenyNotEnoughFund() throws CustomException {
		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3006;
		Long toAccount = (long) 3001;
		Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);
		boolean result = transferService.transferRequest(transaction);
		

	}
	
	/**
	 * Validate To Account during Transfer Money
	 * @throws CustomException
	 */
	@Test(expected = Exception.class)
	public void testToAccountDuringTransferMoeny() throws CustomException  {
		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3001;
		Long toAccount = (long) 3007; //in-valid Account
		Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);
		boolean result = transferService.transferRequest(transaction);
				
	}
	
	/**
	 * Validate From Account during Transfer Money
	 * @throws CustomException
	 */
	@Test(expected = Exception.class)
	public void testFromAccountDuringTransferMoeny() throws CustomException  {
		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3007;//in-valid Account
		Long toAccount = (long) 3001; 
		Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);
		boolean result = transferService.transferRequest(transaction);
				
	}

	
	
	/**
	 * This test case is used to test multiple transaction from one to another
	 * account
	 */
	@Test
	public void testTransferMoenyThreadedTransfer() {

		BigDecimal transferAmount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Long fromAccount = (long) 3003;
		Long toAccount = (long) 3004;

		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Transaction transaction = new Transaction("EUR", transferAmount, fromAccount, toAccount);
						boolean result = transferService.transferRequest(transaction);
						System.out.println("isMoneyTransfered" + result);
					} catch (Exception e) {
						System.out.println("Error" + e.getMessage());
						logger.error("Error occurred during transfer ", e);
					}
				}
			}).start();
		}
	}

}
