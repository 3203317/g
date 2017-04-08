package net.foreworld.gws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class WsServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(WsServer.class);

	private int port;
	private int bossThread;
	private int workerThread;

	public WsServer(int port) {
		this.port = port;
	}

	public WsServer(int port, int bossThread, int workerThread) {
		this(port);
		this.bossThread = bossThread;
		this.workerThread = workerThread;
	}

	private ChannelFuture f;
	private EventLoopGroup bossGroup, workerGroup;

	@Override
	public void start() {
		bossGroup = new NioEventLoopGroup(bossThread);
		workerGroup = new NioEventLoopGroup(workerThread);

		ServerBootstrap b = new ServerBootstrap();

		b.localAddress(port);
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);

		b.option(ChannelOption.SO_BACKLOG, 128);
		b.option(ChannelOption.SO_KEEPALIVE, true);

		b.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast("http-codec", new HttpServerCodec());
				pipe.addLast("aggregator", new HttpObjectAggregator(64 * 1024)); // 65536
				pipe.addLast("http-chunked", new ChunkedWriteHandler());
				pipe.addLast("ws-server-handler", new WsServerHandler());
			}
		});

		try {
			f = b.bind().sync();
			if (f.isSuccess()) {
				logger.info("start {}", port);
				f.channel().closeFuture().sync();
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
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
		try {
			if (null != f)
				f.channel().closeFuture().sync();
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

}
