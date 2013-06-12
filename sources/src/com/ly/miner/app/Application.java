/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONException;

import com.ly.miner.exception.AppConfigException;
import com.ly.miner.exception.CreateAppException;

/**
 * @author jiezhan
 * the class used to describe application.
 */
  class Application {
	  
	 public static final String ACTOR_LIST_KEY = "actorlist";
	 
	 public static final String APP_NAME_KEY = "name";
	 
	 public static final String APP_INSTALLPATH_KEY = "install-path";
	 
	 public static final String APP_CONFIGFILE_NAME = "app.conf";
	  
	 private AppConfig config;
	 
	 final private ActorList actorList = new ActorList();
	 
	 private AppClassLoader classloader;
	 
	 Application(AppConfig config)throws AppConfigException, CreateAppException{
		 this.config = config;
		 String name = this.config.getAppName();
		 classloader = new AppClassLoader(name);
		 createActorList();
	 }
	 
	 void createApp()throws CreateAppException{
		 createActorList();
	 }
	 
	 private void createActorList()throws CreateAppException{
		 try {
			actorList.createActors(config.getConf().getJSONArray(ACTOR_LIST_KEY), classloader);
		} catch (JSONException e) {
			throw new CreateAppException(e);
		}
	 }
	 
	 public String getAppName(){
		 try {
			return config.getAppName();
		} catch (AppConfigException e) {
			e.printStackTrace();
		}return null;
	 }
	 
	 void start(){
		 actorList.start();
	 }
	 
	 void stop(){
		 actorList.stop();
	 }
	
}
