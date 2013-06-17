/**
 * 
 */
package com.ly.miner.app;

import com.ly.miner.exception.CreateAppException;
import com.ly.miner.exception.StartApplicationException;

/**
 * @author jiezhan
 *
 */
public class Miner {

	/**
	 * @param args
	 * @throws CreateAppException 
	 * @throws StartApplicationException 
	 */
	public static void main(String[] args) throws CreateAppException, StartApplicationException {
		
		AppManager.installApp();
		
		AppManager.startApp();

	}

}
