/**
 * 
 */
package com.ly.miner.app;

/**
 * @author jiezhan;
 *
 */
public abstract class AbstractApplicationStart implements IApplicationStart,java.lang.Runnable {
	
	private IApplication application;


	
	@Override
	public IApplication getApplication() {
		
		return application;
	}
	@Override
	final public void run(){
		start();
	}

	
	@Override
	public void setApplication(IApplication app) {
		
		this.application = app;

	}

}
