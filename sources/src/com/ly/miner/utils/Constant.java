/**
 * 
 */
package com.ly.miner.utils;

import java.io.File;

/**
 * @author zhanjie
 * the class used to defined some constant value.
 */
public final class Constant {
	
	public static String MINER_HOME;
	
	public static String MINER_LOG_PATH;
	
	public static String MINER_APPS_PATH;
	
	public static String MINER_CONF_PATH;
	
	public static String MINER_LIB_PATH;
	
	static{
		
		MINER_HOME = System.getenv("MINER_HOME");
		if(!MINER_HOME.endsWith(File.separatorChar+"")){
			
			MINER_HOME += MINER_HOME + File.separatorChar;
		}
		MINER_LOG_PATH += MINER_HOME + "apps" + File.separatorChar; 
		MINER_LOG_PATH += MINER_HOME + "log" + File.separatorChar; 
		MINER_CONF_PATH += MINER_HOME + "conf" + File.separatorChar;
		MINER_LIB_PATH += MINER_LIB_PATH + "lib" + File.separatorChar;
	}

}
