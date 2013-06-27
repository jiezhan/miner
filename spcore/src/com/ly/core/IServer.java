/**
 * 
 */
package com.ly.core;

import com.ly.core.exception.ServerException;

/**
 * @author jiezhan
 *
 */
public interface IServer<T extends IServerConfig> {
	
	void start() throws ServerException;
	
	void stop() throws ServerException;

}
