package net.foreworld.gws.initializer;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author huangxin
 *
 */
@Component
public class WsInitializer extends ChannelInitializer<NioSocketChannel> {

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();
		pipe.addLast(new HttpServerCodec());
		pipe.addLast(new ChunkedWriteHandler());
		pipe.addLast(new HttpObjectAggregator(1024 * 64));
		pipe.addLast(new WebSocketServerProtocolHandler("/chat"));

	}

}
