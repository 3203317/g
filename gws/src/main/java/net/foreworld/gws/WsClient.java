package net.foreworld.gws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class WsClient extends Client {

	private static final Logger logger = LoggerFactory
			.getLogger(WsClient.class);

	private int port;
	private String host;

	public WsClient(String host, int port) {
		this.port = port;
		this.host = host;
	}

	@Override
	public void start() {
		EventLoopGroup group = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();

		b.remoteAddress(new InetSocketAddress(host, port));
		b.group(group);
		b.channel(NioServerSocketChannel.class);

		b.option(ChannelOption.SO_BACKLOG, 128).option(
				ChannelOption.SO_KEEPALIVE, true);

		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {

			}

		});

		try {
			ChannelFuture f = b.connect().sync();
			if (f.isSuccess()) {
				f.channel().closeFuture().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != group) {
				group.shutdownGracefully();
			}
		}
	}

	@Override
	public void stop() {
	}

}
