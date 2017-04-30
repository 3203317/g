package net.foreworld.gws.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.annotation.Resource;

import net.foreworld.gws.codec.MsgCodec;
import net.foreworld.gws.handler.EchoHandler;
import net.foreworld.gws.handler.TimeHandler;

import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin
 *
 */
@Component
public class WsInitializer extends ChannelInitializer<NioSocketChannel> {

	@Resource(name = "timeHandler")
	private TimeHandler timeHandler;

	@Resource(name = "echoHandler")
	private EchoHandler echoHandler;

	@Resource(name = "msgCodec")
	private MsgCodec msgCodec;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		pipe.addLast(new HttpServerCodec());
		pipe.addLast(new HttpObjectAggregator(1024 * 64));
		pipe.addLast(new ChunkedWriteHandler());
		pipe.addLast(new HttpContentCompressor());
		pipe.addLast(new WebSocketServerProtocolHandler("/"));

		pipe.addLast(timeHandler);
	}

}
