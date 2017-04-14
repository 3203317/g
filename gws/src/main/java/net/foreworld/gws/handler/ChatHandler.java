package net.foreworld.gws.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger logger = LoggerFactory
			.getLogger(ChatHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("1");
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()
				+ "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		logger.info("4");
		String resp;
		boolean close = false;
		if (request.isEmpty()) {
			resp = "Please type something.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			resp = "Have a good day!\r\n";
			close = true;
		} else {
			resp = "Did you say '" + request + "'?\r\n";
		}

		ChannelFuture future = ctx.write(resp);
		ctx.flush();
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
