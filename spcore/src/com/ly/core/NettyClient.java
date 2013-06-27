/**
 * 
 */
package com.ly.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictor;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioSocketChannelConfig;
import org.jboss.netty.channel.socket.oio.OioDatagramChannelFactory;
import com.ly.core.exception.ClientException;
import com.ly.core.utils.Constants;

/**
 * @author zhanjie
 *
 */
public abstract class NettyClient extends AbstractClient<SocketClientConfig> {
	
	protected  ChannelFactory factory;
	/**
	 * to do next nioExecutor and logicExecutor;
	 */
	protected final static Executor bossExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
		final AtomicInteger count = new AtomicInteger(1);	
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName("Client-boss-" + count.getAndIncrement());
			return thread;
		}
	});
	
	protected final static Executor workerExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
		final AtomicInteger count = new AtomicInteger(1);		
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName("Client-worker-" + count.getAndIncrement());
			return thread;
		}
	});
	
	protected ClientBootstrap bootstrap;
	
	protected ChannelPipelineFactory pipelineFactory;
	
	protected Channel channel;
	
	protected ChannelFuture future = null;
	
	protected boolean isUDPClient = false;
	
	protected InetSocketAddress serverAddress = null;
	
	protected final static ChannelFactory STATIC_FACTORY = new NioClientSocketChannelFactory(bossExecutor, workerExecutor);

	public NettyClient(SocketClientConfig conf) {
		super(conf);
	}

	@Override
	public void start() throws ClientException {
		
		try{
			bootstrap = new ClientBootstrap(factory);
			pipelineFactory = new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() {
                	ChannelPipeline pipeline = null;
                	try{
                		pipeline = newPipeline();
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                	 return pipeline;
                	}
            };
			
			setOption();
			init();
		}catch(Exception e){
			throw new ClientException(e);
		}
		
	}
	
	private void init() throws ClientException{
		
		if (Constants.UDP.compareToIgnoreCase(conf._protocolType) == 0){
			if (Constants.NIO.compareToIgnoreCase(conf._ioType) == 0){
				createUDPNIOClient();
	    	}else if (Constants.OIO.compareToIgnoreCase(conf._ioType) == 0){
	    		createUDPOIOClient();
	    	}else{
	    		//This is an error
	    		throw new ClientException("The supplied IO Protocol (" + 
	    				conf._ioType + 
	    					") is not supported!!", null);
	    	}
			
    	}else if (Constants.TCP.compareToIgnoreCase(conf._protocolType) == 0){  		
    		if (Constants.NIO.compareToIgnoreCase(conf._ioType) == 0){
    			createNIOClientConnection();
	    	}else if (Constants.OIO.compareToIgnoreCase(conf._ioType) == 0){
				createOIOClientConnection();
	    	}else{
	    		//This is an error
	    		throw new ClientException("The supplied IO Protocol (" + 
	    				conf._ioType + 
	    					") is not supported!!", null);
	    	}
    	}else{
    		//This is an error
    		throw new ClientException("The supplied Transport Protocol (" + 
    				conf._protocolType + 
    					") is not supported!!", null);
    	}
	}
	
	private void createUDPNIOClient()throws ClientException{
		try{
			factory = new NioDatagramChannelFactory(conf._bUseStaticThreadPool ? workerExecutor : Executors.newCachedThreadPool());
			createUDPClient();
		}catch(Exception e){
			throw new ClientException(e);
		}
		
	}
	
	private void createUDPOIOClient()throws ClientException{
		try{
			factory = new OioDatagramChannelFactory(conf._bUseStaticThreadPool ? workerExecutor : Executors.newCachedThreadPool());
			createUDPClient();
		}catch(Exception e){
			throw new ClientException(e);
		}
		
	}
	
	private void createNIOClientConnection()throws ClientException{
		
		if (conf._bUseStaticThreadPool == false){			
			 factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());		
			 bootstrap = new ClientBootstrap(factory);			
		}else{
			bootstrap = new ClientBootstrap(STATIC_FACTORY);
		}

		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		if(_connectionTimeout>0){
			_bootstrap.setOption("connectTimeoutMillis", _connectionTimeout);
		}

		// Start the connection attempt.
		_future = _bootstrap.connect(new InetSocketAddress(
									_clientConfiguration._endPointHostname, 
									_clientConfiguration._endPointPort));
			
		if (_clientConfiguration._synchronousConnection == true){
			_channel = awaitUninterruptibly().getChannel();
//			if (_channel == null || 
//				_channel.isConnected() == false){
//				throw new RouterException("Failed to start the client connection to " + 
//						_clientConfiguration._endPointHostname + ":" + 
//						_clientConfiguration._endPointPort, null);
//			}
			((NioSocketChannelConfig)_channel.getConfig()).
					setSendBufferSize(_clientConfiguration._packetBufferSize);
			if (_clientConfiguration._initialObject != null){
				_channel.write(_clientConfiguration._initialObject);
			}
		}else{
			//Need to setup the future listener.
			_future.addListener(this);
		}
		
	}
	
	private void createOIOClientConnection()throws ClientException{
		
	}
	
	private void createUDPClient() throws ClientException {
		
		try {
			 isUDPClient = true;
			 channel = factory.newChannel(newPipeline());
			((DatagramChannel)channel).getConfig().setBroadcast(false);
			
			//http://java.sun.com/j2se/1.4.2/docs/api/java/net/DatagramSocket.html
			//Changing this value allow the socket to recieve more data without
			//dropping packets.
			((DatagramChannel)channel).getConfig().setReceiveBufferSize(conf._readBufferSize);
			
			serverAddress = new InetSocketAddress(conf._endPointHostname,
												   conf._endPointPort);
			
			((DatagramChannel)channel).getConfig().
						setReceiveBufferSizePredictor(new FixedReceiveBufferSizePredictor(conf._packetBufferSize));
			future = channel.bind(new InetSocketAddress(0));
			awaitUninterruptibly();
		} catch (Exception e) {
			throw new ClientException("Failed to start the socket " + 
						conf._endPointHostname + ":" + 
						conf._endPointPort, e);
		}
	}
	 private ChannelFuture awaitUninterruptibly() {
	        synchronized (future) {
	            while (!future.isDone()) {
	                try {
	                	future.wait(1);
	                } catch (InterruptedException e) {
	                    // Ignore.
	                } finally {
	                }
	            }
	        }
	        return future;
	    }

	@Override
	public void stop() throws ClientException {
		
	}
	
	protected void setOption() throws ClientException{
		
	}
	
	protected abstract ChannelPipeline newPipeline() throws ClientException;
	
}
