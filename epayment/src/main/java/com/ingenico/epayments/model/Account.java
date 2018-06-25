package com.ingenico.epayments.model;

import java.math.BigDecimal;

/**
 * 
 * @author Devoteam
 *
 */
public class Account {

	private Long accountId;
	private String name;
	private BigDecimal balance;
	private String currency;
	

	public Account() {
	}

	public Account(Long id, String name, BigDecimal balance, String currency) {
		super();
		this.accountId = id;
		this.name = name;
		this.balance = balance;
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Account [id=" + accountId + ", name=" + name + ", balance=" + balance + ", currency=" + currency + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long id) {
		this.accountId = id;
	}

}
