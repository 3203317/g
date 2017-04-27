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
public class TimeHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Method.RequestProtobuf req = (Method.RequestProtobuf) msg;
		logger.info(req.getSeqId() + ":" + req.getToken());

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf
				.newBuilder();

		resp.setVersion(req.getVersion());
		resp.setMethod(req.getMethod());
		resp.setSeqId(req.getSeqId() + 1);
		resp.setTimestamp(req.getTimestamp());

		ctx.writeAndFlush(resp);
	}
}
