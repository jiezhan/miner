/**
 * 
 */
package com.ly.core;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictor;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.DefaultServerSocketChannelConfig;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.oio.OioDatagramChannelFactory;
import com.ly.core.exception.ServerException;
import com.ly.core.utils.Constants;
import com.ly.core.utils.Utils;
/**
 * @author jiezhan
 *
 */
public abstract class NettyServer extends AbstractServer<SocketServerConfig> {
	
	final protected ChannelGroup allChannels = new DefaultChannelGroup("netty-server");
	
	protected  ChannelFactory factory;
	/**
	 * to do next nioExecutor and logicExecutor;
	 */
	protected ExecutorService nioExecutor;
	
	protected ExecutorService logicExecutor;
	
	protected ServerBootstrap bootstrap;
	
	protected ChannelPipelineFactory pipelineFactory;
	
	protected Channel channel;
	
	public NettyServer(SocketServerConfig conf) {
		super(conf);
	}

	public void start() throws ServerException{
		try{
			bootstrap = new ServerBootstrap(factory);
			pipelineFactory = new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() {
                	ChannelPipeline pipeline = null;
                	try{
                		pipeline = newPipeline();
                    	pipeline.addFirst("savechanne", new TrackChannelHandler(allChannels));
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                	 return pipeline;
                	
                	}
            };
			
			setOption();
			init();
			allChannels.add(channel);
		}catch(Exception e){
			throw new ServerException(e);
		}
	}
	
	public void stop() throws ServerException{
		try{
			 ChannelGroupFuture future = allChannels.close();
			 future.awaitUninterruptibly();
			 factory.releaseExternalResources();
			 bootstrap = null;
		}catch(Exception e){
			throw new ServerException(e);
		}
		
	}
	
	private void init() throws ServerException{
		if (Constants.UDP.equalsIgnoreCase(conf._transportProtocol)){
			initUdpServer();
		}
		else if (Constants.TCP.equalsIgnoreCase(conf._transportProtocol)){
			startTCPServer();
    	}else{
    		//This is an error
    		throw new ServerException("The supplied Transport Protocol (" + 
    					conf._transportProtocol + 
    					") is not supported!!", null);
    	}
	}
	
	private void initUdpServer() throws ServerException{
		try {
			if (Constants.OIO.compareToIgnoreCase(conf._ioType) == 0){
				factory = new OioDatagramChannelFactory(Executors.newCachedThreadPool());
			}else if (Constants.NIO.compareToIgnoreCase(conf._ioType) == 0){
				factory = new NioDatagramChannelFactory(Executors.newCachedThreadPool());
			}else{
	    		//This is an error
	    		throw new ServerException("The supplied IO Type (" + 
	    					conf._ioType + 
	    					") is not supported!!", null);
			}
			channel = factory.newChannel(pipelineFactory.getPipeline());

			((DatagramChannel)channel).getConfig().setBroadcast(false);
			((DatagramChannel)channel).getConfig().setSendBufferSize(conf._readBufferSize);       
			((DatagramChannel)channel).getConfig().setReceiveBufferSizePredictor(new FixedReceiveBufferSizePredictor(conf._packetBufferSize));
			channel.bind(new InetSocketAddress(InetAddress.getByAddress(Utils.textIPToByteArray(conf._listeningAddr)),conf._listeningPort)).awaitUninterruptibly();		
			
		} catch (Exception exception) {
			throw new ServerException("Error occurred while starting " +
							"the UCP server on port: " + conf._listeningPort, exception);
		}
	}
	
	private void startTCPServer() throws ServerException{
		try{
			factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
			bootstrap = new ServerBootstrap(factory);
			bootstrap.setPipelineFactory(pipelineFactory);		
			channel = bootstrap.bind(new InetSocketAddress(InetAddress.getByAddress(Utils.textIPToByteArray(conf._listeningAddr)),conf._listeningPort));

			if (channel == null || channel.isBound() == false){
				//TODO - what should happen in this case.
				throw new ServerException(
							"failed to start properly on port " + 
							conf._listeningPort, null);
			}else{
				
				
				((DefaultServerSocketChannelConfig)channel.getConfig()).
							setReceiveBufferSize(conf._readBufferSize);
			}
		} catch (RuntimeException exception){
			throw new ServerException("Error occurred while starting " +
					"the TCP server on port: " + conf._listeningPort, exception);
		} catch (UnknownHostException unknownHostException) {
			throw new ServerException("Error occurred while starting " +
					"the TCP server on address: " + conf._listeningAddr +
					" port: " + conf._listeningPort, unknownHostException);
		}
		
	}
	
	protected abstract ChannelPipeline newPipeline() throws ServerException;
	
	protected void setOption() throws ServerException{
		
	}

}
