/**
 * 
 */
package com.ly.core;

/**
 * @author jiezhan
 *
 */
public abstract class AbstractClient<T extends IClientConfig> implements IClient<T> {
	
	protected T conf;
	
	public AbstractClient(T conf){
		this.conf = conf;
	}

}
