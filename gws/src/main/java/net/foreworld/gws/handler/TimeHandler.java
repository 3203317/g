package net.foreworld.gws.handler;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.model.User;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class TimeHandler extends SimpleChannelInboundHandler<Method.RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(TimeHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("{}", cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("{}:{}", msg.getSeqId(), msg.getTimestamp());

		try {
			User.UserProtobuf _user = User.UserProtobuf.parseFrom(msg.getData());
			logger.info("{}:{}", _user.getUserName(), _user.getUserPass());
		} catch (InvalidProtocolBufferException e) {
			logger.error("{}", e);
		}

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

		resp.setVersion(msg.getVersion());
		resp.setMethod(msg.getMethod());
		resp.setSeqId(msg.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
		user.setUserName("黄鑫");
		user.setId(UUID.randomUUID().toString());
		user.setUserPass(UUID.randomUUID().toString());

		resp.setData(user.build().toByteString());

		ctx.writeAndFlush(resp);
	}

}
