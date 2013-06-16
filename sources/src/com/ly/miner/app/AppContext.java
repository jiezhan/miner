/**
 * 
 */
package com.ly.miner.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ly.miner.exception.AppConfigException;
import com.ly.miner.exception.ResourceException;
import com.ly.miner.utils.Constant;

/**
 * @author jiezhan
 * 
 */
public final class AppContext {
	
	private AppConfig appconfig;
	
	public AppContext(AppConfig appconfig){
		this.appconfig = appconfig;
	}
	
	public AppConfig getAppconfig() {
		return appconfig;
	}
	
	public InputStream getResource(String fileName)throws ResourceException{
		final String RESOURCES_STR = "resource";
		String realPath;
		try {
			realPath = Constant.MINER_APPS_PATH + appconfig.getAppName() + File.separatorChar + RESOURCES_STR +  File.separatorChar + fileName;
		} catch (AppConfigException e) {
			throw new ResourceException(e);
		}
		try{
			File file = new File(realPath);
			java.io.InputStream in = new FileInputStream(file); 
			return in;
		}catch(Exception e){
			throw new ResourceException(e);
		}
	}

}
