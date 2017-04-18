package net.foreworld.gws.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author huangxin
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private ByteBuf firstMsg;

	public TimeClientHandler() {
		byte[] req = "Query Time Order".getBytes();
		firstMsg = Unpooled.buffer(req.length);
		firstMsg.writeBytes(req);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(firstMsg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

	}

}
