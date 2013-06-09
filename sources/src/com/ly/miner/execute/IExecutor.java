/**
 * 
 */
package com.ly.miner.execute;

import java.util.concurrent.ExecutorService;

/**
 * @author zhanjie
 * the interface used to run all task which implements java.lang.Runnable.
 *
 */
public abstract class IExecutor {
	
	protected ExecutorService executor;
	
	protected String threadName;
	// execute task.
	final public void execute(Runnable command){
		executor.execute(command);
	}
	
	// stop Executor.
	final public void stop(){
		executor.shutdownNow();
	}
}
