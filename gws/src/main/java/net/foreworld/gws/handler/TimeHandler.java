package net.foreworld.gws.handler;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info(msg.getSeqId() + ":" + msg.getTimestamp());

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

		resp.setVersion(msg.getVersion());
		resp.setMethod(msg.getMethod());
		resp.setSeqId(msg.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
		user.setUserName("王莹");
		user.setId(UUID.randomUUID().toString());
		user.setUserPass(UUID.randomUUID().toString());

		resp.setData(user.build().toByteString());

		ctx.writeAndFlush(resp);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
