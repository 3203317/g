package net.foreworld.gws.initializer;

import static io.netty.buffer.Unpooled.wrappedBuffer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.foreworld.gws.codec.JSONCodec;
import net.foreworld.gws.codec.MsgCodec;
import net.foreworld.gws.handler.EchoHandler;
import net.foreworld.gws.handler.FireNextHandler;
import net.foreworld.gws.handler.LoginHandler;
import net.foreworld.gws.handler.TimeHandler;
import net.foreworld.gws.protobuf.Method;

/**
 *
 * @author huangxin
 *
 */
@Component
public class WsInitializer extends ChannelInitializer<NioSocketChannel> {

	@Resource(name = "loginHandler")
	private LoginHandler loginHandler;

	@Resource(name = "fireNextHandler")
	private FireNextHandler fireNextHandler;

	@Resource(name = "timeHandler")
	private TimeHandler timeHandler;

	@Resource(name = "echoHandler")
	private EchoHandler echoHandler;

	@Resource(name = "msgCodec")
	private MsgCodec msgCodec;

	@Resource
	private JSONCodec jSONCodec;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		pipe.addLast(new HttpServerCodec());
		pipe.addLast(new HttpObjectAggregator(1024 * 64));
		pipe.addLast(new ChunkedWriteHandler());
		pipe.addLast(new HttpContentCompressor());
		pipe.addLast(new WebSocketServerProtocolHandler("/", null, true));

		pipe.addLast(new WebSocketServerCompressionHandler());
		// pipe.addLast(fireNextHandler);

		pipe.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
			@Override
			protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
				ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
				out.add(buf);
				buf.retain();
			}
		});

		// pipe.addLast(new ProtobufVarint32FrameDecoder());
		pipe.addLast(new ProtobufDecoder(Method.RequestProtobuf.getDefaultInstance()));
		pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
		// pipe.addLast(new ProtobufEncoder());

		pipe.addLast(new MessageToMessageEncoder<MessageLiteOrBuilder>() {
			@Override
			protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out)
					throws Exception {
				ByteBuf result = null;
				if (msg instanceof MessageLite) {
					result = wrappedBuffer(((MessageLite) msg).toByteArray());
				} else if (msg instanceof MessageLite.Builder) {
					result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
				}
				WebSocketFrame frame = new BinaryWebSocketFrame(result);
				out.add(frame);
			}
		});

		// pipe.addLast(msgCodec);
		pipe.addLast(timeHandler);
		// pipe.addLast("login", loginHandler);
	}

}
