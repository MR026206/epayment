package com.ingenico.epayments.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ingenico.epayments.exception.CustomException;
import com.ingenico.epayments.model.Account;

/**
 * This class is used to maintain DB transaction for the Account & Transfer
 * Service
 * 
 * @author Devoteam
 *
 */
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private final static String SQL_CREATE_ACC = "INSERT INTO ingenico_epayment.account (AccountId, Name, Balance,CurrencyCode) VALUES (?, ?, ?,?)";
	private final static String SQL_LOCK_ACC_BY_ID = "SELECT * FROM ingenico_epayment.account WHERE AccountId = ? FOR UPDATE";
	private final static String SQL_ACC_BY_ID = "SELECT * FROM ingenico_epayment.account WHERE AccountId = ? ";
	private final static String SQL_UPDATE_ACC_BALANCE = "UPDATE ingenico_epayment.account SET Balance = ? WHERE AccountId = ? ";

	private JdbcTemplate jdbcTemplate;

	public AccountDaoImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Create Account
	 */
	@Override
	public int createAccount(Account account) throws CustomException {
		int result = 0;
		try {
			result = jdbcTemplate.update(SQL_CREATE_ACC, new Object[] { account.getAccountId(), account.getName(),
					account.getBalance(), account.getCurrency() });

		} catch (DataAccessException e) {
			throw new CustomException(e.getMessage());
		}

		return result;
	}

	/**
	 * getAccountObjById
	 */
	@Override
	public Account lockAccountForTransaction(long accountId) throws CustomException {
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(SQL_LOCK_ACC_BY_ID, new Object[] { accountId },
					new RowMapper<Account>() {
						@Override
						public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
							Account account = new Account();
							account.setAccountId(rs.getLong("AccountId"));
							account.setName(rs.getString("Name"));
							account.setBalance(rs.getBigDecimal("Balance"));
							account.setCurrency(rs.getString("CurrencyCode"));
							return account;
						}
					});

		} catch (DataAccessException e) {
			throw new CustomException(e.getMessage());
		}
		return account;
	}

	/**
	 * Update Account Balance
	 */
	@Override
	public int updateAccountBalance(long accountId, BigDecimal amount) throws CustomException {
		int result = 0;
		try {
			result = jdbcTemplate.update(SQL_UPDATE_ACC_BALANCE, new Object[] { amount, accountId });
		} catch (DataAccessException e) {
			throw new CustomException(e.getMessage());
		}
		return result;
	}
	
	


	@Override
	public Account getAccountInfo(long accountId) throws CustomException {
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(SQL_ACC_BY_ID, new Object[] { accountId }, new RowMapper<Account>() {
				@Override
				public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
					Account account = new Account();
					account.setAccountId(rs.getLong("AccountId"));
					account.setName(rs.getString("Name"));
					account.setBalance(rs.getBigDecimal("Balance"));
					account.setCurrency(rs.getString("CurrencyCode"));
					return account;
				}
			});

		} catch (DataAccessException e) {
			throw new CustomException(e.getMessage());
		}
		return account;
	}

}
