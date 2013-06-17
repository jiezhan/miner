/**
 * 
 */
package com.ly.miner.app;
import org.json.JSONException;
import com.ly.miner.exception.StartApplicationException;
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

	
	@SuppressWarnings("rawtypes")
	@Override
	 public void start()throws StartApplicationException {
		try{
			actorList.start();
		}catch(Exception e){
			throw new StartApplicationException(e);
		}
		String startClassStr = null;
		try {
			startClassStr = config.getConf().getString("start");
		} catch (JSONException e) {
			throw new StartApplicationException(e);
		}
		Class startClass = null;
		try {
			startClass = Class.forName(startClassStr.trim());
		} catch (ClassNotFoundException e) {
			throw new StartApplicationException(e);
		}
		IApplicationStart start;
		try {
			start = (IApplicationStart)startClass.newInstance();
		} catch (Exception e) {
			throw new StartApplicationException(e);
		} 
		try {
			start.setApplication(this);
			start.start();
		} catch (Exception e) {
			throw new StartApplicationException(e);
		} 
		 
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
