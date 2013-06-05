package com.ly.miner.exception;

public class ActionException extends Exception {
	
	
	private static final long serialVersionUID = 390954653714599087L;

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
	
	 public Throwable getRootCause() {
	        return getCause();
	}
}