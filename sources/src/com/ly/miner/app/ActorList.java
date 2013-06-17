/**
 * 
 */
package com.ly.miner.app;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ly.miner.action.MinerAction;
import com.ly.miner.actor.Actor;
import com.ly.miner.actor.MinerActor;
import com.ly.miner.exception.CreateAppException;
import com.ly.miner.exception.MailBoxException;
import com.ly.miner.execute.FixedThreadPoolExecutor;
import com.ly.miner.mailbox.BlockQueueMailBox;
import com.ly.miner.utils.Event;

/**
 * @author jiezhan
 *
 */
 class ActorList extends MinerActor {
	
	protected final java.util.Map<String,MinerActor> actorList = new java.util.HashMap<String,MinerActor>();
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
	
	final void doJob(Event event){
		try {
			this.getMailbox().put(event);
		} catch (MailBoxException e) {
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	void createActors(JSONObject config,IApplication application) throws CreateAppException{
		
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
					throw new CreateAppException(e);
				}
				if(actorList.get(name) != null){
					throw new CreateAppException("the actor name is already exist.");
				}
				try {
					Class mc = null;
					try {
						mc = Class.forName(actionClass.trim());
					} catch (ClassNotFoundException e) {
						throw new CreateAppException(e);
					}
					MinerAction action;
					try {
						action = (MinerAction)mc.newInstance();
						action.setApplication(application);
					} catch (Exception e) {
						throw new CreateAppException(e);
					} 
					MinerActor mactor = new MinerActor();
					mactor.setName(name);
					mactor.setAction(action);
					mactor.setExecute(new FixedThreadPoolExecutor(("actor[" + name+"]"),execute));
					mactor.setMailbox(new BlockQueueMailBox(DEFAULT_MAILBOX_SIZE));
					actorList.put(name, mactor);
					if(i == 0){
						this.setMailbox(mactor.getMailbox());
					}
				} catch (Exception e) {
					throw new CreateAppException(e);
				}
				
			}
		}
		
	}
	void arrangeActors(JSONObject config) throws CreateAppException{
		if(config == null){
			throw new CreateAppException("actorlist is null.");
		}
		JSONArray rules = null;
		try{
			rules = config.getJSONArray(ACTORS_RULES_STR);
			 if(rules.length() < 1){
				 throw new CreateAppException("rules is null.");
			 }
		}catch(JSONException e){
			throw new CreateAppException("actors is null.");
		}
		for(int i =0 ; i < rules.length(); i++){
			JSONObject rule = null;
			try {
				rule = rules.getJSONObject(i);
			} catch (JSONException e) {
				throw new CreateAppException("config rule is error.");
			}
			if(rule != null){
				String rulestr;
				try{
					rulestr = rule.getString("rule");
					String[] temp = rulestr.split("\\|");
					if(temp.length != 2){
						throw new CreateAppException("config rule is error. have not the char of |");
					}
					String preactorStr = temp[0];
					if(actorList.get(preactorStr) == null){
						throw new CreateAppException("config rule is error. do not find the actor[" + preactorStr +"]");
					}
					String[] nextactorsStr = temp[1].split(",");
					for(String nextactorStr : nextactorsStr){
						if(actorList.get(nextactorStr) == null){
							throw new CreateAppException("config rule is error. do not find the actor[" + nextactorStr +"]");
						}
					}
					List<Actor>  nexts = new ArrayList<Actor>();
					for(String nextactorStr : nextactorsStr){
						nexts.add(this.actorList.get(nextactorStr));
					}
					actorList.get(preactorStr).setNext(nexts);
				}catch(JSONException e){
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
