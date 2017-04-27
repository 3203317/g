package net.foreworld.gws.client.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.method.user.Login;
import net.foreworld.gws.protobuf.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

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
		Method.RequestProtobuf.Builder req = Method.RequestProtobuf
				.newBuilder();
		req.setVersion(101);
		req.setMethod(1);
		req.setSeqId(56);
		req.setTimestamp(System.currentTimeMillis());

		Login.RequestProtobuf.Builder login = Login.RequestProtobuf
				.newBuilder();
		login.setUserName("黄鑫");
		login.setUserPass("123456hx");

		req.setData(login.build().toByteString());

		ctx.writeAndFlush(req.build());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Method.ResponseProtobuf req = (Method.ResponseProtobuf) msg;
		logger.info(req.getSeqId() + "");

		try {
			User.UserProtobuf user = User.UserProtobuf.parseFrom(req.getData());
			logger.info(user.getId() + ":" + user.getUserName());
		} catch (InvalidProtocolBufferException e) {
			logger.error("InvalidProtocolBufferException", e);
		}
	}
}
