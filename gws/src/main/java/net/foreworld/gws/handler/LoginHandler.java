package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.model.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin
 *
 */
public class LoginHandler extends SimpleChannelInboundHandler<Login> {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Login msg)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
