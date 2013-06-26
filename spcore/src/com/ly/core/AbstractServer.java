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
public abstract class AbstractServer implements IServer {
	
	protected JSONObject conf;
	
	final public void config(JSONObject conf) throws ServerException{
		this.conf = conf;
	}
	
}
