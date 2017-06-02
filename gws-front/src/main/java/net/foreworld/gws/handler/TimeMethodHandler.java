package net.foreworld.gws.handler;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class TimeMethodHandler extends SimpleChannelInboundHandler<RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(TimeMethodHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("method: {}", msg.getMethod());

		if (95 != msg.getMethod()) {
			ctx.fireChannelRead(msg);
			return;
		}

		ChannelFuture future = ctx.close();

		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
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

}
