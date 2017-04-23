package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.protobuf.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class TimeHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Login.LoginRequest login = (Login.LoginRequest) msg;
		logger.info(login.getUserName() + ":" + login.getUserPass());

		Login.LoginResponse.Builder resp = Login.LoginResponse.newBuilder();
		resp.setSuccess(true);
		resp.setCode("001");
		resp.setMsg("登陆成功");
		ctx.writeAndFlush(resp);
	}
}
