/**
 * 
 */
package com.ly.miner.app;

/**
 * @author jiezhan
 * the interface used to describe application is wrote by developer.
 */
public interface IApplication {
	
	String getName();
	
	String getInstallPath();
	
	java.io.InputStream getResource(String url);

}
