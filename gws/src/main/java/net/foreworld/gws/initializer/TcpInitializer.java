package net.foreworld.gws.initializer;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author huangxin
 *
 */
@Component
public class TcpInitializer extends ChannelInitializer<NioSocketChannel> {

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

	}

}
