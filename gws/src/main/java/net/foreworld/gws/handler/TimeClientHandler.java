package net.foreworld.gws.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.protobuf.Method;

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
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeClientHandler.class);

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
		req.setMethod(202);
		req.setSeqId("" + Math.random());
		req.setToken("" + System.currentTimeMillis());

		ctx.writeAndFlush(req.build());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println(msg.toString());
	}
}
