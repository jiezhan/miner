/**
 * 
 */
package com.ly.core.exception;

/**
 * @author jiezhan
 *
 */
public class ClientException extends BaseException {

	
	private static final long serialVersionUID = -8574681424533361171L;

	public ClientException() {
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

}

