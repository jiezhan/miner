/**
 * 
 */
package com.ly.miner.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import com.ly.miner.exception.AppClassLoaderException;
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
	
	final static private Map<String,IApplication> apps = new java.util.HashMap<String, IApplication>();
	
	public static void startApp(){
		for(IApplication app : apps.values()){
			System.out.println(app.getAppName());
			app.start();
		}
	}
	/**
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
				IApplication application = null;
				try {
					 application = classLoad.getObject(com.ly.miner.app.DefaultApplication.class, "com.ly.miner.app.DefaultApplication");
				} catch (AppClassLoaderException e) {
					throw new CreateAppException(e);
				}
				application.createApp(appconfStr);
				apps.put(application.getAppName(), application);
				
			}
		}
	
	}
	**/
	
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
				//IApplication application = null;
				Object appobject = null;
				Class<?> appclass = null;
				try {
					 appobject = classLoad.getObject("com.ly.miner.app.DefaultApplication");
					 appclass = classLoad.getClass("com.ly.miner.app.DefaultApplication");
				} catch (AppClassLoaderException e) {
					throw new CreateAppException(e);
				}
				Method mothod;
				try {
					mothod = appclass.getMethod("createApp", String.class);
				} catch (Exception e) {
					throw new CreateAppException(e);
				} 
				if(mothod != null){
					try {
						mothod.invoke(appobject, appconfStr);
					} catch (Exception e) {
						throw new CreateAppException(e);
					} 
				}
			//	apps.put("appdemo", (DefaultApplication)appobject);
				
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
