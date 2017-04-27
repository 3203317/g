package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

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
public class EchoHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(EchoHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Method.RequestProtobuf req = (Method.RequestProtobuf) msg;
		logger.info(req.getSeqId() + ":" + req.getTimestamp());

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf
				.newBuilder();

		resp.setVersion(req.getVersion());
		resp.setMethod(req.getMethod());
		resp.setSeqId(req.getSeqId() + 1);
		resp.setTimestamp(req.getTimestamp());

		try {
			Login.RequestProtobuf login = Login.RequestProtobuf.parseFrom(req
					.getData());
			logger.info(login.getUserName() + ":" + login.getUserPass());
		} catch (InvalidProtocolBufferException e) {
			logger.error("InvalidProtocolBufferException", e);
		}

		User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
		user.setUserName("王莹");
		user.setId(UUID.randomUUID().toString());

		resp.setData(user.build().toByteString());

		ctx.writeAndFlush(resp);
	}
}
