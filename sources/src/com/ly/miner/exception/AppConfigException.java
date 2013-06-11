/**
 * 
 */
package com.ly.miner.exception;

/**
 * @author jiezhan
 * 
 * application config exception
 *
 */
public class AppConfigException extends MinerException {

	private static final long serialVersionUID = 395316121636295515L;
	
	public AppConfigException() {
	}

	public AppConfigException(String message) {
		super(message);
	}

	public AppConfigException(Throwable cause) {
		super(cause);
	}

	public AppConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
