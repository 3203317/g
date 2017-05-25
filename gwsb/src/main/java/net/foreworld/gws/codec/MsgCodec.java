package net.foreworld.gws.codec;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.Method.ResponseProtobuf;

import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class MsgCodec extends
		MessageToMessageCodec<RequestProtobuf, ResponseProtobuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseProtobuf msg,
			List<Object> out) throws Exception {
		out.add(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, RequestProtobuf msg,
			List<Object> out) throws Exception {
		out.add(msg);
	}

}
