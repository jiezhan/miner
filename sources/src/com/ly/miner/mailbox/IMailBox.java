/**
 * 
 */
package com.ly.miner.mailbox;

import com.ly.miner.exception.MailBoxException;

/**
 * @author zhanjie
 *
 */
public interface IMailBox<T> {
	
	T task()throws MailBoxException;
	
	void put(T item)throws MailBoxException;
	
	int size();
	
	int getCapacity();
	
	void clear();

}
