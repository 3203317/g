package net.foreworld.gws.client.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.User.UserLoginProtobuf;

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
public class LoginHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		RequestProtobuf.Builder req = RequestProtobuf.newBuilder();
		req.setVersion(101);
		req.setMethod(1);
		req.setSeqId(56);
		req.setTimestamp(System.currentTimeMillis());

		UserLoginProtobuf.Builder login = UserLoginProtobuf.newBuilder();
		login.setCode("123456");

		req.setData(login.build().toByteString());

		ctx.writeAndFlush(req.build());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ResponseProtobuf req = (ResponseProtobuf) msg;
		logger.info(req.getSeqId() + "");
	}
}
