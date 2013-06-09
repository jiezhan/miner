/**
 * 
 */
package com.ly.miner.actor;

import java.util.List;

import com.ly.miner.action.IAction;
import com.ly.miner.exception.MailBoxException;
import com.ly.miner.execute.IExecutor;
import com.ly.miner.mailbox.IMailBox;

/**
 * @author zhanjie
 *
 */
abstract class Actor<T> implements java.lang.Runnable{
	
	private IExecutor execute;
	
	private IMailBox<T> mailbox;
	
	private IAction<T> action;
	
	private List<Actor<?>> next;
	
	private volatile boolean isStoped;
	
	public void start(){
		execute.execute(this);
	}
	
	@Override
	public void run(){
		
		while(!isStoped){
			
			T item ;
			try {
				 item = mailbox.task();
			} catch (MailBoxException e) {
				break;
			}
			try{
				if(item != null){
					action.doit(item);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void stopActor(){
		isStoped = true;
		this.execute.stop();
		this.mailbox.clear();
	}

	public void setExecute(IExecutor execute) {
		this.execute = execute;
	}

	public IMailBox<T> getMailbox() {
		return mailbox;
	}

	public IAction<T> getAction() {
		return action;
	}

	public List<Actor<?>> getNext() {
		return next;
	}

	public void setNext(List<Actor<?>> next) {
		this.next = next;
	}
}
