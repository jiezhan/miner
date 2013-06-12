/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONArray;
import com.ly.miner.actor.MinerActor;
import com.ly.miner.exception.CreateAppException;


/**
 * @author jiezhan
 *
 */
 class ActorList extends MinerActor {
	
	final java.util.List<MinerActor> actorList = new java.util.LinkedList<MinerActor>();
	
	
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
	
	void createActors(JSONArray config,AppClassLoader classloader)throws CreateAppException{
		
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
