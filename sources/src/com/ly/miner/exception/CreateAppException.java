/**
 * 
 */
package com.ly.miner.exception;

/**
 * @author jiezhan
 *
 */
public class CreateAppException extends MinerException {

	private static final long serialVersionUID = -8761208290230216759L;
	
	public CreateAppException() {
	}

	public CreateAppException(String message) {
		super(message);
	}

	public CreateAppException(Throwable cause) {
		super(cause);
	}

	public CreateAppException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
