/**
 * 
 */
package com.ly.miner.mailbox;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.ly.miner.exception.MailBoxException;
import com.ly.miner.utils.Event;


/**
 * @author zhanjie
 *
 */
public class BlockQueueMailBox implements IMailBox<Event> {
	
	private final BlockingQueue<Event> box;
	
	private int capacity = 1;
	
	public BlockQueueMailBox(){
		box = new ArrayBlockingQueue<Event>(capacity);
	}
	
	public BlockQueueMailBox(int capacity){
		this.capacity = capacity;
		box = new ArrayBlockingQueue<Event>(capacity);
	}

	@Override
	public Event task() throws MailBoxException{
		
		try {
			return box.take();
		} catch (InterruptedException e) {
			throw new  MailBoxException(e);
		} 
		
	}

	@Override
	public void put(Event item) throws MailBoxException{
		
		try {
			box.put(item);
		} catch (InterruptedException e) {
			throw new  MailBoxException(e);
		}
		
	}

	@Override
	public int size() {
		return box.size();
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public void clear() {
		box.clear();
	}

}
