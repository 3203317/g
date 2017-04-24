package net.foreworld.gws.codec;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

import net.foreworld.gws.protobuf.Method.Request;
import net.foreworld.gws.protobuf.Method.Response;

import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class MsgCodec extends MessageToMessageCodec<Request, Response> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Response msg,
			List<Object> out) throws Exception {

		out.add(msg);

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Request msg,
			List<Object> out) throws Exception {
		out.add(msg);
	}

}
