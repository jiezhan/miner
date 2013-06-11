/**
 * 
 */
package com.ly.miner.exception;

/**
 * @author jiezhan
 *
 */
public class AppClassLoaderException extends MinerException {

	private static final long serialVersionUID = 5407840390102431318L;
	
	public AppClassLoaderException() {
	}

	public AppClassLoaderException(String message) {
		super(message);
	}

	public AppClassLoaderException(Throwable cause) {
		super(cause);
	}

	public AppClassLoaderException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
