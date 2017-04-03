package net.foreworld.gws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.foreworld.gws.handler.WsServerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class WsServer extends Server {

	private static final Logger logger = LoggerFactory
			.getLogger(WsServer.class);

	private int port;

	public WsServer(int port) {
		this.port = port;
	}

	@Override
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap b = new ServerBootstrap();

		b.localAddress(port);
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);

		b.option(ChannelOption.SO_BACKLOG, 128).option(
				ChannelOption.SO_KEEPALIVE, true);

		b.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast("http-codec", new HttpServerCodec());
				pipe.addLast("aggregator", new HttpObjectAggregator(65536));
				pipe.addLast("http-chunked", new ChunkedWriteHandler());
				pipe.addLast("handler", new WsServerHandler());
			}
		});

		try {
			ChannelFuture f = b.bind().sync();
			if (f.isSuccess()) {
				logger.info("start {}", port);
				f.channel().closeFuture().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != bossGroup) {
				bossGroup.shutdownGracefully();
			}
			if (null != workerGroup) {
				workerGroup.shutdownGracefully();
			}
		}
	}

	@Override
	public void stop() {
	}

}
