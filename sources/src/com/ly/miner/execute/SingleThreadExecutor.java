/**
 * 
 */
package com.ly.miner.execute;

/**
 * @author zhanjie
 * 
 */
public final class SingleThreadExecutor extends IExecutor {

	public SingleThreadExecutor(String threadName) {
		this.executor = java.util.concurrent.Executors
				.newSingleThreadExecutor(new MCAThreadFactory(threadName));
		this.threadName = threadName;
	}
}
