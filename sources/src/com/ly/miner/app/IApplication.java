package com.ly.miner.app;

import com.ly.miner.exception.CreateAppException;
import com.ly.miner.exception.StartApplicationException;
import com.ly.miner.utils.Event;
/**
 * @author jiezhan
 *
 */
public interface IApplication {
	
	 void createApp(String confStr) throws CreateAppException;
	 
	 String getAppName();
	 
	 void start()throws StartApplicationException ;
	 
	 void stop();
	 
	 AppConfig getAppConfig();
	 
	 AppContext getAppContext();
	 
	 ActorList getActorList();
	 
	 void doActor(Event event);

}
