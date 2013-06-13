/**
 * 
 */
package com.ly.apps.demo;

import com.ly.miner.action.MinerAction;
import com.ly.miner.exception.ActionException;
import com.ly.miner.utils.Event;

/**
 * @author jiezhan
 *
 */
public class HelloWorldAction extends MinerAction{

	@Override
	public void doit(Event arg0) throws ActionException {
		System.out.println("hello world...");
		
	}

}
