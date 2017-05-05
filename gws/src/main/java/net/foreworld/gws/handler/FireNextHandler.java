package net.foreworld.gws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class FireNextHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FireNextHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
		ctx.fireChannelRead(buf);
	}
}
