/**
 * 
 */
package com.ly.miner.exception;

/**
 * @author jiezhan
 *
 */
public class ResourceException extends MinerException {

	private static final long serialVersionUID = -2265483243938428928L;
	
	public ResourceException() {
	}

	public ResourceException(String message) {
		super(message);
	}

	public ResourceException(Throwable cause) {
		super(cause);
	}

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
