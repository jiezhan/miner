/**
 * 
 */
package com.ly.core;

import com.ly.core.exception.ClientException;

/**
 * @author zhanjie
 *
 */
public interface IClient <T extends IClientConfig>{
	
	void start() throws ClientException;
	
	void stop() throws ClientException;

}
