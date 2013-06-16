/**
 * 
 */
package com.ly.miner.app;

import org.json.JSONException;
import com.ly.miner.exception.AppConfigException;
import com.ly.miner.exception.CreateAppException;
import com.ly.miner.utils.Constant;

/**
 * @author jiezhan
 *
 */
public class DefaultApplication extends AbstractApplication {
	
	@Override
	public void createApp(String confStr) throws CreateAppException {

		try {
			config = new AppConfig(confStr);
		} catch (AppConfigException e) {
			throw new CreateAppException(e);
		}
		try {
			name = config.getAppName();
		} catch (AppConfigException e) {
			throw new CreateAppException(e);
		}
		context = new AppContext(config);
		createActorList();
		createOther();
	}
	
	 private void createActorList()throws CreateAppException{
		 try {
				actorList.createActors(config.getConf().getJSONObject(Constant.ACTOR_LIST_KEY),this);
				actorList.arrangeActors(config.getConf().getJSONObject(Constant.ACTOR_LIST_KEY));
			} catch (JSONException e) {
				throw new CreateAppException(e);
			}
	 }
	 
	 private void createOther()throws CreateAppException{
		 
	 }
}
