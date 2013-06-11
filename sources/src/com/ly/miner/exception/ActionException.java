package com.ly.miner.exception;

public class ActionException extends MinerException {

	
	private static final long serialVersionUID = 7001084086848693036L;
	
	public ActionException() {
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
}