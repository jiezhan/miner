/**
 * 
 */
package com.ly.miner.exception;

/**
 * @author jiezhan
 *
 */
public class StartApplicationException extends Exception {

	
	private static final long serialVersionUID = -7346210835257858267L;

	public StartApplicationException() {
	}

	public StartApplicationException(String message) {
		super(message);
	}

	public StartApplicationException(Throwable cause) {
		super(cause);
	}

	public StartApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	 public Throwable getRootCause() {
	        return getCause();
	}

}
