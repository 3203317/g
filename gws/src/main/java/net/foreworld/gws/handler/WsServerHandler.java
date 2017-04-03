package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class WsServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(WsServerHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

	}

}
