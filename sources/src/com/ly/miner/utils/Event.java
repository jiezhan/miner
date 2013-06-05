/**
 * 
 */
package com.ly.miner.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiezhan
 * the class used to describe the source data of mine. 
 *
 */
final public class Event implements java.io.Serializable{

	private static final long serialVersionUID = -7683557802749161506L;

	private int id;
	
	private String name;
	
	private Object data;
	
	final private Map<Object,Object> others = new HashMap<Object, Object>();

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getData() {
		return data;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void put(String key,Object value){
		this.others.put(key, value);
	}
	
	public Object get(String key){
		return others.get(key);
	}

}
