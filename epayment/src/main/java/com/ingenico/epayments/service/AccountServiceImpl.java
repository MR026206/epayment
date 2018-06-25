package com.ingenico.epayments.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingenico.epayments.dao.AccountDao;
import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;

/**
 * This Service class is used to Maintain Account activity i.e. Account creation 
 * @author Devoteam
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	/**
	 * This method is used to create Monetary account min with balance 20 Euro.
	 * validate min balance & Account holder Name
	 */
	@Override
	public boolean createMonetaryAccount(Account ac) throws CustomException {

		BigDecimal minBalance = new BigDecimal(20).setScale(4, RoundingMode.HALF_EVEN);

		// Validate Min Balance
		if (ac.getBalance().compareTo(minBalance) < 0) {
			throw new CustomException("Monetary Account can not be created - Required Min Balance - "
					+ minBalance.toString() + " " + ac.getCurrency());
		}

		// Validate Account holder Name
		if (StringUtils.isEmpty(ac.getName())) {
			throw new CustomException("Account holder name is mandatory - can not be blank. ");
		}

		int result = accountDao.createAccount(ac);
		boolean isAccountCrated = (result != 0);
		return isAccountCrated;
	}

}
