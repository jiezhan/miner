/**
 * 
 */
package com.ly.miner.app;

import com.ly.miner.exception.AppConfigException;

/**
 * @author jiezhan
 * the class used to describe application.
 */
  class Application {
	  
	 private AppConfig config;
	 
	 private ActorList actorList;
	 
	 private AppClassLoader classloader;
	 
	 Application(AppConfig config)throws AppConfigException{
		 this.config = config;
		 String name = this.config.getAppName();
		 classloader = new AppClassLoader(name);
	 }
	 

}
