/**
 * 
 */
package com.ly.miner.execute;

/**
 * @author zhanjie
 *
 */
public class CacheThreadExecutor extends IExecutor {
	
	public CacheThreadExecutor(String threadName) {
		this.executor = java.util.concurrent.Executors.newCachedThreadPool(new MCAThreadFactory(threadName));
		this.threadName = threadName;
		
	}

}
