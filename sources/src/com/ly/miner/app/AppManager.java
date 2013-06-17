/**
 * 
 */
package com.ly.miner.app;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.googlecode.transloader.ObjectWrapper;
import com.googlecode.transloader.Transloader;
import com.ly.miner.exception.AppClassLoaderException;
import com.ly.miner.exception.AppConfigException;
import com.ly.miner.exception.CreateAppException;
import com.ly.miner.exception.StartApplicationException;
import com.ly.miner.utils.Constant;
import com.ly.miner.utils.Helper;

/**
 * @author jiezhan
 * the class used to manage application.
 *
 */
final class AppManager {
	
	final static private Map<String,IApplication> apps = new java.util.HashMap<String, IApplication>();
	
	public static void startApp()throws StartApplicationException {
		for(IApplication app : apps.values()){
			System.out.println(app.getAppName());
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
				String configPathStr = appPath + f.getName() + File.separatorChar + "conf" + File.separatorChar + Constant.APP_CONFIGFILE_NAME;
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
				AppClassLoader classLoad;
				try {
					 classLoad = new AppClassLoader(config.getAppName());
				} catch (AppConfigException e) {
					throw new CreateAppException("create appclassload exception.");
				}
				Object appobject = null;
				try {
					 appobject = classLoad.getObject("com.ly.miner.app.DefaultApplication");
				} catch (AppClassLoaderException e) {
					throw new CreateAppException(e);
				}
				ObjectWrapper applicationWrapped = null;
				try {
					Transloader transloader = Transloader.DEFAULT;
					applicationWrapped = transloader.wrap(appobject);
				} catch (Exception e) {
					throw new CreateAppException(e);
				} 
				IApplication iapp = null;
				try{
					iapp = (IApplication) applicationWrapped.makeCastableTo(IApplication.class);
					iapp.createApp(appconfStr);
				}catch(Exception e){
					throw new CreateAppException(e);
				}
				if(iapp != null)
				apps.put(iapp.getAppName(), iapp);
			}
		}
	
	}
	


	public static void uninstallApp(){
		for(IApplication app : apps.values()){
			app.stop();
		}
		apps.clear();
	}
	
	public static void uninstallApp(String name){
		IApplication app = apps.get(name);
		if(app!=null){
			apps.clear();
		}
		apps.remove(name);
	}

}
