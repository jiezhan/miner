/**
 * 
 */
package com.ly.miner.action;

import com.ly.miner.app.AppContext;
import com.ly.miner.utils.Event;

/**
 * @author zhanjie
 *
 */
public abstract class MinerAction implements IAction<Event> {
	
	protected AppContext context;
}
