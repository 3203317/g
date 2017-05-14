package net.foreworld.gws.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;

import javax.annotation.Resource;

import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.method.user.Login;
import net.foreworld.util.StringUtil;

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
public class LoginHandler extends
		SimpleChannelInboundHandler<Method.RequestProtobuf> {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginHandler.class);

	@Resource(name = "timeVersionHandler")
	private TimeVersionHandler timeVersionHandler;

	@Resource(name = "timeHandler")
	private TimeHandler timeHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg)
			throws Exception {
		logger.info(msg.getVersion() + ":" + msg.getMethod() + ":"
				+ msg.getSeqId() + ":" + msg.getTimestamp());

		if (95 == msg.getMethod()) {

			try {

				Login.RequestProtobuf req = Login.RequestProtobuf.parseFrom(msg
						.getData());
				String code = req.getCode();

				String token = verify(code);

				logger.info(code + ":" + token);

				if (null != token) {

					Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf
							.newBuilder();

					resp.setVersion(msg.getVersion());
					resp.setMethod(msg.getMethod());
					resp.setSeqId(msg.getSeqId());
					resp.setTimestamp(System.currentTimeMillis());

					Login.ResponseProtobuf.Builder data = Login.ResponseProtobuf
							.newBuilder();
					data.setToken(token);

					resp.setData(data.build().toByteString());

					ctx.writeAndFlush(resp);

					ctx.pipeline().replace(this, "time", timeHandler);
					ctx.pipeline().addLast(timeVersionHandler);

					return;
				}

			} catch (InvalidProtocolBufferException e) {
				logger.error("InvalidProtocolBufferException", e);
			}
		}

		ChannelFuture future = ctx.close();

		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				SocketAddress addr = ctx.channel().remoteAddress();

				if (future.isSuccess()) {
					logger.info("ctx close: {}", addr);
					return;
				}

				logger.info("ctx close failure: {}", addr);
				ctx.close();
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error("", cause);
		ctx.close();
	}

	/**
	 * 授权码验证
	 *
	 * @param code
	 * @return
	 */
	private String verify(String code) {

		code = StringUtil.isEmpty(code);

		if (null == code) {
			return null;
		}

		return "token";
	}

}
