/**
 * 
 */
package com.ly.miner.app;

import com.ly.miner.exception.CreateAppException;

/**
 * @author jiezhan
 *
 */
public class Miner {

	/**
	 * @param args
	 * @throws CreateAppException 
	 */
	public static void main(String[] args) throws CreateAppException {
		
		AppManager.installApp();
		
		AppManager.startApp();

	}

}
