package net.foreworld.gws.initializer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.foreworld.gws.codec.JSONCodec;
import net.foreworld.gws.handler.EchoHandler;

/**
 *
 * @author huangxin
 *
 */
@Component
public class WsNormalInitializer extends ChannelInitializer<NioSocketChannel> {

	@Resource(name = "echoHandler")
	private EchoHandler echoHandler;

	@Resource
	private JSONCodec jSONCodec;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		pipe.addLast(new LoggingHandler(LogLevel.INFO));

		pipe.addLast(new HttpServerCodec());
		pipe.addLast(new HttpObjectAggregator(1024 * 64));
		pipe.addLast(new ChunkedWriteHandler());
		pipe.addLast(new HttpContentCompressor());
		pipe.addLast(new WebSocketServerProtocolHandler("/", null, false));
		pipe.addLast(new WebSocketServerCompressionHandler());

		pipe.addLast(jSONCodec);
		pipe.addLast(echoHandler);
	}

}