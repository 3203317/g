package net.foreworld.gws.handler;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
public class LoginHandler extends SimpleChannelInboundHandler<Method.RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	@Resource(name = "timeHandler")
	private TimeHandler timeHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info(msg.getSeqId() + ":" + msg.getTimestamp());

		if (95 != msg.getMethod()) {

			ChannelFuture future = ctx.close();

			future.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess())
						ctx.close();

					logger.info("ctx close: {}", ctx.channel().remoteAddress());
				}
			});

			return;
		}

		try {
			User.UserProtobuf _user = User.UserProtobuf.parseFrom(msg.getData());
			logger.info(_user.getUserName() + ":" + _user.getUserPass());
		} catch (InvalidProtocolBufferException e) {
			logger.error("InvalidProtocolBufferException", e);
		}

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

		resp.setVersion(msg.getVersion());
		resp.setMethod(msg.getMethod());
		resp.setSeqId(msg.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
		user.setUserName("吴鹏");
		user.setId(UUID.randomUUID().toString());
		user.setUserPass(UUID.randomUUID().toString());

		resp.setData(user.build().toByteString());

		ctx.writeAndFlush(resp);

		ctx.pipeline().replace(this, "time", timeHandler);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("TimeHandler", cause);
		ctx.close();
	}

}
