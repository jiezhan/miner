/**
 * 
 */
package com.ly.miner.action;

import com.ly.miner.app.AppConfig;
import com.ly.miner.app.AppContext;
import com.ly.miner.app.IApplication;
import com.ly.miner.exception.ActionException;
import com.ly.miner.utils.Event;

/**
 * @author zhanjie
 *
 */
public abstract class  MinerAction  {
	
	public abstract void doit(Event t) throws ActionException;
	
	public void init() throws ActionException{}
	
	public void destroy() throws ActionException{}
	
	protected IApplication application ;
	
	public AppContext getAppContext(){
		return application.getAppContext();
	}
	
	public AppConfig getAppConfig(){
		return application.getAppConfig();
	}

	public IApplication getApplication() {
		return application;
	}

	public void setApplication(IApplication application) {
		this.application = application;
	}
	
}
