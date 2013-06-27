/**
 * 
 */
package com.ly.core;

import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import com.ly.core.utils.Constants;


/**
 * @author zhanjie
 *
 */
public class SocketServerConfig implements IServerConfig{

	/**
	 * Servers can have multiple IP addresses, setting this will
	 * allow you to bind to something other than the default ip address.
	 */
	public String	_listeningAddr = "0.0.0.0";
	
	/**
	 * This is the port that this server will listen for all incoming messages
	 */
	public int		_listeningPort = 80;
	
	/**
	 * The transport protocol of the server can be TCP|UDP
	 */
	public String	_transportProtocol = Constants.TCP;
	
	/**
	 * Server ioType can be either NIO|OIO
	 */
	public String	_ioType = Constants.NIO;
	
	/**
	 * @see http://java.sun.com/j2se/1.4.2/docs/api/java/net/DatagramSocket.html
	 * Changing this value allow the socket to recieve more data without 
	 * dropping packets.
	 */
	public int		_readBufferSize = Constants.READ_BUFFER_SIZE;
	
	/**
	 * This is the default size for the packets being received. We want to set
	 * this to some average size for HTTP packets, setting it to high means that
	 * we over allocate memory and setting to low means we under allocate memory
	 * and need to adjust the buffer.
	 */
	public int		_packetBufferSize = Constants.PACKET_BUFFER_SIZE;
	
	/**
	 * The ID of the server can be used to assign specific actions to. actions
	 * can be assigned in the plugin.xml file for a module.
	 */
	public String 	_id = "";
		
	/**
	 * This notify the server that is will use SSL. 
	 */
	public boolean  _bEnableTLS = false;
	
	/**
	 * Worker threads maximum allow you to set the number of 
	 * threads that netty will execute. Adjust this will adjust the
	 * number of concurrent message received that can be made.  Netty
	 * will be default set this to the number of processors * 2
	 */
	public int  _maximumWorkerCount = 10;
	

	
	/**
	 * @param readerIdleTimeSeconds
	 * an {@link IdleStateEvent} whose state is
	 * {@link IdleState#READER_IDLE} will be triggered when no read
	 * was performed for the specified period of time. Specify
	 * {@code 0} to disable.
	 */
	public int _readerIdleTimeSeconds = 0;

	/**
	 * @param writerIdleTimeSeconds
	 * an {@link IdleStateEvent} whose state is
	 * {@link IdleState#WRITER_IDLE} will be triggered when no write
	 * was performed for the specified period of time. Specify
	 * {@code 0} to disable.
	 */
	public int _writerIdleTimeSeconds = 0;

	/**
	 * @param allIdleTimeSeconds
	 * an {@link IdleStateEvent} whose state is
	 * {@link IdleState#ALL_IDLE} will be triggered when neither read
	 * nor write was performed for the specified period of time.
	 * Specify {@code 0} to disable.
	 */
	public int _allIdleTimeSeconds = 30;


	
}

