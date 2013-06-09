/**
 * 
 */
package com.ly.miner.mailbox;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.ly.miner.exception.MailBoxException;


/**
 * @author zhanjie
 *
 */
 class BlockQueueMailBox<T> implements IMailBox<T> {
	
	private final BlockingQueue<T> box;
	
	private int capacity = 1;
	
	public BlockQueueMailBox(){
		box = new ArrayBlockingQueue<T>(capacity);
	}
	
	public BlockQueueMailBox(int capacity){
		this.capacity = capacity;
		box = new ArrayBlockingQueue<T>(capacity);
	}

	@Override
	public T task() throws MailBoxException{
		
		try {
			return box.take();
		} catch (InterruptedException e) {
			throw new  MailBoxException(e);
		} 
		
	}

	@Override
	public void put(T item) throws MailBoxException{
		
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
