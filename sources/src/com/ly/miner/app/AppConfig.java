/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONException;
import org.json.JSONObject;

import com.ly.miner.exception.AppConfigException;


final public class AppConfig {

	private JSONObject conf ;
	
	public AppConfig(String str) throws AppConfigException{
		
		try {
			conf = new JSONObject(str);
		} catch (JSONException e) {
			throw new AppConfigException(e);
		}
	}
	
	public String getAppName()throws AppConfigException{
		try {
			return conf.getString(Application.APP_NAME_KEY);
		} catch (JSONException e) {
			throw new AppConfigException(e);
		}
	}
	
	public String getAppInstallPath()throws AppConfigException{
		try {
			return conf.getString(Application.APP_INSTALLPATH_KEY);
		} catch (JSONException e) {
			throw new AppConfigException(e);
		}
	}

	public JSONObject getConf() {
		return conf;
	}
}
