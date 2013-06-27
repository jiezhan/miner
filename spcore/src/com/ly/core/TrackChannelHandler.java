/**
 * 
 */
package com.ly.core;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;

/**
 * @author zhanjie
 *
 */
public class TrackChannelHandler extends SimpleChannelHandler {
	
	private ChannelGroup allChannels ;
	
	protected TrackChannelHandler(ChannelGroup allChannels){
		this.allChannels = allChannels;
	}
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		allChannels.add(e.getChannel());
		ctx.sendUpstream(e);
	}

}
