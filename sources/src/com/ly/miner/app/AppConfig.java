/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONObject;


final public class AppConfig {

	private JSONObject conf ;
	
	public AppConfig(String str){
		
		conf = new JSONObject(str);
	}
}
