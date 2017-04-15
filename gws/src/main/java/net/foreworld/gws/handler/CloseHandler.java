package net.foreworld.gws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class CloseHandler extends ChannelOutboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CloseHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
		logger.info("close channel");
		ctx.close(promise);
	}

}
