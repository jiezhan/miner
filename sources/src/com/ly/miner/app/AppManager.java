/**
 * 
 */
package com.ly.miner.app;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.ly.miner.exception.AppConfigException;
import com.ly.miner.exception.CreateAppException;
import com.ly.miner.utils.Constant;
import com.ly.miner.utils.Helper;

/**
 * @author jiezhan
 * the class used to manage application.
 *
 */
final class AppManager {
	
	final static private Map<String,Application> apps = new java.util.HashMap<String, Application>();
	
	public static void startApp(){
		for(Application app : apps.values()){
			app.start();
		}
	}
	
	public static void installApp()throws CreateAppException{
		String appPath = Constant.MINER_APPS_PATH;
		File appfile = new File(appPath.trim());
		if (!appfile.exists()) {
			throw new CreateAppException("the dir of" + appPath +" is not exists.");
		}
		File[] files = appfile.listFiles();
		if (files != null) {
			for(File f : files){
				if(!f.isDirectory()){
					throw new CreateAppException("the file of" + f.getAbsolutePath() +" is not directory.");
				}
				String configPathStr = appPath + f.getName() + File.separatorChar + Application.APP_CONFIGFILE_NAME;
				String appconfStr;
				try {
					appconfStr = Helper.readFile(configPathStr);
				} catch (IOException e) {
					throw new CreateAppException(e);
				}
				AppConfig config = null;
				try {
					 config = new AppConfig(appconfStr);
				} catch (AppConfigException e) {
					throw new CreateAppException(e);
				}
				Application application = null;
				try {
					 application = new Application(config);
				} catch (AppConfigException e) {
					throw new CreateAppException(e);
				}
				apps.put(application.getAppName(), application);
				
			}
		}

	
	}
	
	public static void uninstallApp(){
		for(Application app : apps.values()){
			app.stop();
		}
		apps.clear();
	}
	
	public static void uninstallApp(String name){
		Application app = apps.get(name);
		if(app!=null){
			apps.clear();
		}
		apps.remove(name);
	}

}
