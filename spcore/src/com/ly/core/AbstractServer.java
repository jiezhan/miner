/**
 * 
 */
package com.ly.core;


/**
 * @author jiezhan
 *
 */
public abstract class AbstractServer<T extends IServerConfig> implements IServer<T> {
	
	protected T conf;
	
	public AbstractServer(T conf){
		this.conf = conf;
	}
	
	
}
