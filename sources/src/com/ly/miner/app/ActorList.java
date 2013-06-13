/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ly.miner.action.MinerAction;
import com.ly.miner.actor.MinerActor;
import com.ly.miner.exception.ActionException;
import com.ly.miner.exception.AppClassLoaderException;
import com.ly.miner.exception.CreateAppException;


/**
 * @author jiezhan
 *
 */
 class ActorList extends MinerActor {
	
	private final java.util.List<MinerActor> actorList = new java.util.LinkedList<MinerActor>();
	private final String ACTORS_STR = "actors";
	private final String ACTORS_RULES_STR = "rules";
	
	
	@Override
	public void start(){
		for(MinerActor actor : actorList){
			actor.start();
		}
	}
	
	@Override
	public void stop(){
		for(MinerActor actor : actorList){
			actor.stop();
		}
	}
	
	void createActors(JSONObject config,AppClassLoader classloader)throws CreateAppException{
		
		if(config == null){
			throw new CreateAppException("actorlist is null.");
		}
		JSONArray actors = null;
		try{
			 actors = config.getJSONArray(ACTORS_STR);
			 if(actors.length() < 1){
				 throw new CreateAppException("actors is null.");
			 }
		}catch(JSONException e){
			throw new CreateAppException("actors is null.");
		}
		for(int i =0 ; i < actors.length(); i++){
			JSONObject actor = null;
			try {
				actor = actors.getJSONObject(i);
			} catch (JSONException e) {
				throw new CreateAppException("config actors is error.");
			}
			if(actor != null){
				String name;
				String actionClass;
				String execute;
				try{
					 name = actor.getString("name");
					 actionClass = actor.getString("class");
					 execute = actor.getString("execute");
				}catch(JSONException e){
					throw new CreateAppException("config actor list is error.");
				}
				try {
					MinerAction action = classloader.getObject(MinerAction.class, actionClass.trim());
					try {
						action.doit(null);
					} catch (ActionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (AppClassLoaderException e) {
					throw new CreateAppException(e);
				}
				
			}
		}
		
	}
	void remove(MinerActor actor){
		actorList.remove(actor);
	}
	
	void remove(String actorName){
		MinerActor target = null;
		for(MinerActor actor : actorList){
			if(actor.getName().equals(actorName)){
				target = actor;
				break;
			}
		}
		if(target != null){
			remove(target);
		}
	}
	
	void clear(){
		actorList.clear();
	}

}
