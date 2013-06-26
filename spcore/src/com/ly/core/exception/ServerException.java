/**
 * 
 */
package com.ly.core.exception;

/**
 * @author jiezhan
 *
 */
public class ServerException extends BaseException {

	private static final long serialVersionUID = -6777691485133581327L;
	
	public ServerException() {
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
