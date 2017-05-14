package net.foreworld.gws.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.net.SocketAddress;

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
public class ProtocolSafeHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(ProtocolSafeHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error("", cause);
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		if (msg instanceof WebSocketFrame) {

			if (!(msg instanceof BinaryWebSocketFrame)) {

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

				return;
			}
		} else if (msg instanceof FullHttpRequest) {
			logger.info("first request: {}", ctx.channel().remoteAddress());
		}

		ctx.fireChannelRead(msg);
	}
}
