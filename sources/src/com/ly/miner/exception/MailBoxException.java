package com.ly.miner.exception;

public class MailBoxException extends Exception {
	
	private static final long serialVersionUID = -6474160991692623546L;

	public MailBoxException() {
	}

	public MailBoxException(String message) {
		super(message);
	}

	public MailBoxException(Throwable cause) {
		super(cause);
	}

	public MailBoxException(String message, Throwable cause) {
		super(message, cause);
	}
	
	 public Throwable getRootCause() {
	        return getCause();
	}
}