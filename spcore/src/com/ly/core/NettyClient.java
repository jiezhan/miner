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
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictor;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.DefaultSocketChannelConfig;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioSocketChannelConfig;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.oio.OioDatagramChannelFactory;
import com.ly.core.exception.ClientException;
import com.ly.core.utils.Constants;

/**
 * @author zhanjie
 *
 */
public abstract class NettyClient extends AbstractClient<SocketClientConfig> implements ChannelFutureListener   {
	
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
	
	//if the client tried to write with out an connection
		//store the data until connected. This is used when we
		//are delaying the Client.
	protected Object delayedWriteObject = null;

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
		if(conf._connectionTimeout > 0){
			bootstrap.setOption("connectTimeoutMillis", conf._connectionTimeout);
		}

		// Start the connection attempt.
		future = bootstrap.connect(new InetSocketAddress(
									conf._endPointHostname, 
									conf._endPointPort));
			
		if (conf._synchronousConnection == true){
			channel = awaitUninterruptibly().getChannel();
			((NioSocketChannelConfig)channel.getConfig()).
					setSendBufferSize(conf._packetBufferSize);
			if (conf._initialObject != null){
				channel.write(conf._initialObject);
			}
		}else{
			future.addListener(this);
		}
		
	}
	
	
	private void createOIOClientConnection()throws ClientException{
		
		factory = new OioClientSocketChannelFactory(
				Executors.newCachedThreadPool());		

		bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		
		// Start the connection attempt.
		future = bootstrap.connect(
						new InetSocketAddress(
									conf._endPointHostname,
									conf._endPointPort));
		channel = future.awaitUninterruptibly().getChannel();
		//TODO does OIO don't have asychronized data send?
		
//		if (_channel == null || 
//			_channel.isConnected() == false){
//			throw new RouterException("Failed to start the client connection to " + 
//					_clientConfiguration._endPointHostname + ":" + 
//					_clientConfiguration._endPointPort, null);
//		}

 		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
       
        //Need to set the sockets window size to gain better performance.
		DefaultSocketChannelConfig config = (DefaultSocketChannelConfig)channel.getConfig();
		config.setReceiveBufferSize(conf._readBufferSize);
		
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
		//This will close the socket.  This happens asynchronously.
		if (channel != null){
			ChannelFuture future = channel.close();
			future.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture future)
						throws Exception {
					if (future.isDone()){
						//check timeout handle exist or not
						if (bootstrap != null && bootstrap.getPipelineFactory() != null 
								&& bootstrap.getPipelineFactory().getPipeline() != null && bootstrap.getPipelineFactory().getPipeline().get("timeout")!=null)
							bootstrap.getPipelineFactory().getPipeline().remove("timeout");
						//release external resources (mzhang)
//						if(!_clientConfiguration._bUseStaticThreadPool && _factory != null){
//							_factory.releaseExternalResources(); // Can not call this in NIO thread
//						}
						
						//This will stop the timeout handler
						if(conf._bUseStaticThreadPool && factory != null){
							factory.releaseExternalResources();
							factory = null;
						}
						bootstrap = null;
					}
				}
				
			});	
			
			
		}
		
	}
	
	protected void setOption() throws ClientException{
		
	}
	
	protected abstract ChannelPipeline newPipeline() throws ClientException;
	
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		boolean bDone = future.isDone();
		if (bDone == true){

			
			//this sync control could avoid the new status resource in the cache.
			synchronized(this){
				
				channel = future.getChannel();
				
				if(channel.isConnected()){
					((NioSocketChannelConfig)channel.getConfig()).
									setSendBufferSize(conf._packetBufferSize);
					
					if (conf._initialObject != null){
						//If someone asked to write data the we can actually write it now.
						channel.write(conf._initialObject);
					}else if (delayedWriteObject != null){
						//If someone asked to write data the we can actually write it now.						
						channel.write(delayedWriteObject);
					}
				}
				
			}

		}
	}
	/**
	 * This method will send data to the a server.  If the channel has
	 * not been fully connected it will store the data in a pending write
	 * array. Once connected the data will be pulled from the array and 
	 * sent to the server.
	 * 
	 * This method will also check and see if the server is UDP or TCP.
	 * If the server is UDP then the server address will be supplied to the
	 * write method.
	 */
    public void write(Object data){   
    	
    	//If the channel is null then it hasn't been created yet so let
    	//accumulate the things to write.
    	
    	if (channel == null){

    		synchronized(this){
    			if(channel==null){
    	    		delayedWriteObject = data;
    	    		return;
    			}
    		}
    	}
    	
    	//channel is not null.
    	if (isUDPClient){
    			channel.write(data, serverAddress);
        		delayedWriteObject = null;
    		}else if (channel.isConnected() == true &&
    				  channel.isOpen() == true && 
    				  channel.isWritable() == true){
    			channel.write(data);
        		delayedWriteObject = null;
    		}
    		
    }
	
}
