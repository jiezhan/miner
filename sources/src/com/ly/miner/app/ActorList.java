/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ly.miner.action.MinerAction;
import com.ly.miner.actor.MinerActor;
import com.ly.miner.exception.AppClassLoaderException;
import com.ly.miner.exception.CreateAppException;
import com.ly.miner.execute.FixedThreadPoolExecutor;
import com.ly.miner.mailbox.BlockQueueMailBox;


/**
 * @author jiezhan
 *
 */
 class ActorList extends MinerActor {
	
	private final java.util.Map<String,MinerActor> actorList = new java.util.HashMap<String,MinerActor>();
	private final String ACTORS_STR = "actors";
	private final String ACTORS_RULES_STR = "rules";
	private final int DEFAULT_MAILBOX_SIZE = 5 ;
	
	
	@Override
	public void start(){
		for(MinerActor actor : actorList.values()){
			actor.start();
		}
	}
	
	@Override
	public void stop(){
		for(MinerActor actor : actorList.values()){
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
				int execute;
				try{
					 name = actor.getString("name");
					 actionClass = actor.getString("class");
					 execute = actor.getInt("execute");
				}catch(JSONException e){
					throw new CreateAppException("config actor list is error.");
				}
				try {
					MinerAction action = classloader.getObject(MinerAction.class, actionClass.trim());
					if(actorList.get(name) != null){
						throw new CreateAppException("the actor name is already exist.");
					}
					MinerActor mactor = new MinerActor();
					mactor.setAction(action);
					mactor.setExecute(new FixedThreadPoolExecutor(("actor[" + name+"]"),execute));
					mactor.setMailbox(new BlockQueueMailBox(DEFAULT_MAILBOX_SIZE));
					actorList.put(name, mactor);
				} catch (AppClassLoaderException e) {
					throw new CreateAppException(e);
				}
				
			}
		}
		
	}
	void remove(MinerActor actor){
		remove(actor.getName());
	}
	
	void remove(String actorName){
		actorList.remove(actorName);
	}
	
	void clear(){
		actorList.clear();
	}

}
