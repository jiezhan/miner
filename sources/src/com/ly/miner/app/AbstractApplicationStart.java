/**
 * 
 */
package com.ly.miner.app;

/**
 * @author jiezhan;
 *
 */
public abstract class AbstractApplicationStart implements IApplicationStart {
	
	private IApplication application;


	
	@Override
	public IApplication getApplication() {
		
		return application;
	}

	
	@Override
	public void setApplication(IApplication app) {
		
		this.application = app;

	}

}
