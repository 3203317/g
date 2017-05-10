package net.foreworld.gws.codec;

import static io.netty.buffer.Unpooled.wrappedBuffer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class BinaryBuildEncode extends MessageToMessageEncoder<MessageLiteOrBuilder> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
		ByteBuf result = null;
		if (msg instanceof MessageLite) {
			result = wrappedBuffer(((MessageLite) msg).toByteArray());
		} else if (msg instanceof MessageLite.Builder) {
			result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
		}
		WebSocketFrame frame = new BinaryWebSocketFrame(result);
		out.add(frame);
	}

}
