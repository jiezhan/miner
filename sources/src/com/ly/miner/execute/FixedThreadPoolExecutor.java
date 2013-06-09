/**
 * 
 */
package com.ly.miner.execute;

/**
 * @author zhanjie
 * 
 */
public final class FixedThreadPoolExecutor extends IExecutor{
	
	public FixedThreadPoolExecutor(String threadName,int threadNumber) {
		this.executor = java.util.concurrent.Executors
				.newFixedThreadPool(threadNumber,new MCAThreadFactory(threadName));
		this.threadName = threadName;
		
	}

}
