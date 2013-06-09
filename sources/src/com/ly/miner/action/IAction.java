/**
 * 
 */
package com.ly.miner.action;

import com.ly.miner.exception.ActionException;

/**
 * @author jiezhan
 *  the interface used to export to application.
 */
 public interface IAction<T> {
	
	void doit(T t)throws ActionException;

}
