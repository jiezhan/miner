/**
 * 
 */
package com.ly.miner.action;

import com.ly.miner.exception.ActionException;
import com.ly.miner.utils.Event;

/**
 * @author jiezhan
 *  the interface used to export to application.
 */
public interface IAction {
	
	void doit(Event event)throws ActionException;

}
