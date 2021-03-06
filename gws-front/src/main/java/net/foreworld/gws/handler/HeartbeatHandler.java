package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;

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
public class HeartbeatHandler extends
		SimpleChannelInboundHandler<RequestProtobuf> {

	private static final Logger logger = LoggerFactory
			.getLogger(HeartbeatHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg)
			throws Exception {
		logger.info("method: {}", msg.getMethod());

		if (666 == msg.getMethod()) {
			ctx.flush();
			return;
		}

		ctx.fireChannelRead(msg);
	}

}
