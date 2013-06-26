/**
 * 
 */
package com.ly.core;

import org.json.JSONObject;

import com.ly.core.exception.ServerException;

/**
 * @author jiezhan
 *
 */
public interface IServer {
	
	void config(JSONObject conf) throws ServerException;
	
	void start() throws ServerException;
	
	void stop() throws ServerException;

}
