package com.ly.miner.exception;
/**
 * @author jiezhan
 * 
 * miner base exception
 *
 */
public class MinerException extends Exception {

	private static final long serialVersionUID = -2977479512954979606L;
	
	public MinerException() {
	}

	public MinerException(String message) {
		super(message);
	}

	public MinerException(Throwable cause) {
		super(cause);
	}

	public MinerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	 public Throwable getRootCause() {
	        return getCause();
	}

}
