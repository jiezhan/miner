/**
 * 
 */
package com.ly.apps.demo;

import com.ly.miner.app.AbstractApplicationStart;
import com.ly.miner.utils.Event;

/**
 * @author zhanjie
 *
 */
public class Start extends AbstractApplicationStart{
	
	public void start(){
		while(true){
			Event e = new Event();
			this.getApplication().doActor(e);
			try {
				Thread.sleep(1000*10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
