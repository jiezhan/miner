/**
 * 
 */
package com.ly.core.exception;

/**
 * @author jiezhan
 *
 */
public class BaseException extends Exception {


	private static final long serialVersionUID = -5357915141496174218L;

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	 public Throwable getRootCause() {
	        return getCause();
	}

}

