package net.foreworld.gws.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import net.foreworld.gws.handler.TimeHandler;

/**
 * 
 * @author huangxin
 *
 */
@Component
public class TcpInitializer extends ChannelInitializer<NioSocketChannel> {

	@Autowired
	private TimeHandler timeHandler;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
		pipe.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));

		pipe.addLast(new StringDecoder());
		pipe.addLast(timeHandler);
	}

}
