package net.foreworld.gws.codec;

import java.util.List;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class MsgCodec extends MessageToMessageCodec<RequestProtobuf, ResponseProtobuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseProtobuf msg, List<Object> out) throws Exception {
		out.add(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, RequestProtobuf msg, List<Object> out) throws Exception {
		out.add(msg);
	}

}
