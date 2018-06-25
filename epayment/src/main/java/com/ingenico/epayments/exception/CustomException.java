package com.ingenico.epayments.exception;

/**
 * This Custom Exception wraps checked/unchecked Std java exception and enrich then with a custom error.
 * @author Devoteam
 *
 */
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
