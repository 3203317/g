package net.foreworld.gws.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 *
 * @author huangxin
 *
 */
public class MsgCodec extends MessageToMessageCodec<TextWebSocketFrame, Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

}
