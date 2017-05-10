package net.foreworld.gws.handler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class ProtocolSafeHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ProtocolSafeHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		if (msg instanceof WebSocketFrame) {

			if (!(msg instanceof BinaryWebSocketFrame)) {

				ChannelFuture future = ctx.close();

				future.addListener(new ChannelFutureListener() {

					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if (!future.isSuccess()) {
							ctx.close();
						}
						InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
						String incoming = addr.getAddress().getHostAddress();
						logger.info("ctx close: {}", incoming);
					}
				});

				return;
			}
		} else if (msg instanceof FullHttpRequest) {
			InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
			String incoming = addr.getAddress().getHostAddress();
			logger.info("ctx first request: {}", incoming);
		}

		ctx.fireChannelRead(msg);
	}
}
