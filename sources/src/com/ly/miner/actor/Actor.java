/**
 * 
 */
package com.ly.miner.actor;

import java.util.List;

import com.ly.miner.action.MinerAction;
import com.ly.miner.exception.MailBoxException;
import com.ly.miner.execute.IExecutor;
import com.ly.miner.mailbox.IMailBox;
import com.ly.miner.utils.Event;

/**
 * @author zhanjie
 *
 */
 public abstract class Actor implements java.lang.Runnable{
	
	private IExecutor execute;
	
	private IMailBox<Event> mailbox;
	
	private MinerAction action;
	
	private List<Actor> next;
	
	private volatile boolean isStoped = false;
	
	private String name;
	
	public void start(){
		System.out.println("starting actor " + name);
		execute.execute(this);
	}
	
	@Override
	 public void run(){
		
		Event item ;
		while(!isStoped){
			
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
			if(next != null && next.size() > 0){
				for(Actor n : next){
					try {
						n.getMailbox().put(item);
					} catch (MailBoxException e) {
						break;
					}
				}
			}
		}
		
	}
	
	public void stop(){
		System.out.println("stoping actor " + name);
		isStoped = true;
		this.execute.stop();
		this.mailbox.clear();
	}

	public void setExecute(IExecutor execute) {
		this.execute = execute;
	}

	public IMailBox<Event> getMailbox() {
		return mailbox;
	}

	public void setMailbox(IMailBox<Event> mailbox) {
		this.mailbox = mailbox;
	}

	public MinerAction getAction() {
		return action;
	}

	public void setAction(MinerAction action) {
		this.action = action;
	}

	public List<Actor> getNext() {
		return next;
	}

	public void setNext(List<Actor> next) {
		this.next = next;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
