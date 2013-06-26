/**
 * 
 */
package com.ly.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import com.ly.core.exception.ServerException;

/**
 * @author jiezhan
 *
 */
public abstract class NettyServer extends AbstractServer {
	
	final protected ChannelGroup allChannels = new DefaultChannelGroup("netty-server");
	
	protected  ChannelFactory factory;
	
	protected ExecutorService nioExecutor;
	
	protected ExecutorService logicExecutor;
	
	protected ServerBootstrap bootstrap;

	public void start() throws ServerException{
		try{
			bootstrap = new ServerBootstrap(factory);
			bootstrap.setPipelineFactory(newPipelineFactory());
			setOption();
			Channel channel = bootstrap.bind(new InetSocketAddress(conf.getInt("port")));
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
	
	protected abstract ChannelPipelineFactory newPipelineFactory() throws ServerException;
	
	protected void setOption() throws ServerException{
		
	}

}
