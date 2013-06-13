package com.ly.miner.app;

import java.io.File;

import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import com.ly.miner.exception.AppClassLoaderException;
import com.ly.miner.utils.Constant;

/**
 * @author jiezhan
 * 
 */
 class AppClassLoader{
	
	private final JarClassLoader minerClassLoad = new JarClassLoader();
	
	private final JarClassLoader appClassLoad = new JarClassLoader();
	
	private final JclObjectFactory factory = JclObjectFactory.getInstance();
	
	private String name;
	
	AppClassLoader(String name){
		this.name = name;
		minerClassLoad.add(Constant.MINER_LIB_PATH);
		appClassLoad.add(Constant.MINER_APPS_PATH+this.name+File.separatorChar+"lib"+File.separatorChar);
	}
	
    Object getObject(String className)throws AppClassLoaderException{
    	try{
    		Object obj = factory.create(appClassLoad, className);
    		if(obj == null){
    			obj = factory.create(minerClassLoad, className);
    		}
    		if(obj == null){
    			throw new AppClassLoaderException("class not found.");
    		}
    		return obj;
    	}catch(Exception e){
    		throw new AppClassLoaderException(e);
    	}
		
	}
	 
    @SuppressWarnings("unchecked")
	<T> T getObject(Class<T> t, String className)throws AppClassLoaderException{
    	
    	try{
    		// Set default to cglib (from version 2.2.1)
  		  ProxyProviderFactory.setDefaultProxyProvider( new CglibProxyProvider() );

  		  //Create a factory of castable objects/proxies
  		  JclObjectFactory factory = JclObjectFactory.getInstance(true);

  		  //Create and cast object of loaded class
  		  
  		  T mi = (T) factory.create(appClassLoad,className);
  		  if(mi == null){
  			  mi = (T) factory.create(minerClassLoad,className);
  		  }
  		  if(mi == null){
  				throw new AppClassLoaderException("class not found.");
  			}
  		 return mi;
    	}catch(Exception e){
    		throw new AppClassLoaderException(e);
    	}
		
	}
	
}