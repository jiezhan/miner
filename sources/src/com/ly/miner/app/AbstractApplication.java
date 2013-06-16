/**
 * 
 */
package com.ly.miner.app;

import com.ly.miner.utils.Event;

/**
 * @author jiezhan
 *
 */
public abstract class AbstractApplication implements IApplication {
	
	 final protected ActorList actorList = new ActorList();
	 
	 protected AppConfig config;
	 
	 protected AppContext context;
	 
	 protected String name;

	@Override
	public String getAppName(){
		 return name;
	}

	
	@Override
	final public void start() {
		 actorList.start();
	}

	
	@Override
	final public void stop() {
		actorList.stop();
	}

	
	@Override
	final public AppConfig getAppConfig() {
		return config;
	}

	
	@Override
	final public AppContext getAppContext() {
		return context;
	}
	
	@Override
	final public ActorList getActorList(){
		return actorList;
	}
	
	@Override
	final public void doActor(Event event){
		actorList.doJob(event);
	 }

}
