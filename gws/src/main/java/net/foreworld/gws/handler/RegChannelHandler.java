package net.foreworld.gws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Method.ResponseProtobuf.Builder;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class RegChannelHandler extends SimpleChannelInboundHandler<Builder> {

	private static final Logger logger = LoggerFactory.getLogger(RegChannelHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("{}", cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Builder msg) throws Exception {
		logger.info("channelRead0");
		ctx.pipeline().remove(this);
		ctx.writeAndFlush(msg);
	}

}
