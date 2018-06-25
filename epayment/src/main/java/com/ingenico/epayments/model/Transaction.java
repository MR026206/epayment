package com.ingenico.epayments.model;

import java.math.BigDecimal;

/**
 * 
 * @author Devoteam
 *
 */
public class Transaction {

	private String currencyCode;
	private BigDecimal amount;
	private Long fromAccountId;
	private Long toAccountId;
	
	
	@Override
	public String toString() {
		return "Transaction [currencyCode=" + currencyCode + ", amount=" + amount + ", fromAccountId=" + fromAccountId
				+ ", toAccountId=" + toAccountId + "]";
	}

	public Transaction() {
	}

	public Transaction(String currencyCode, BigDecimal amount, Long fromAccountId, Long toAccountId) {
		this.currencyCode = currencyCode;
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

}
