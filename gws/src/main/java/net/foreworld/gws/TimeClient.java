package net.foreworld.gws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.foreworld.gws.handler.TimeClientHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class TimeClient extends Server {

	private static final Logger logger = LoggerFactory
			.getLogger(TimeClient.class);

	private int port;
	private String host;
	private int workerThread;

	public TimeClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	private ChannelFuture f;
	private EventLoopGroup workerGroup;

	@Override
	public void start() {
		workerGroup = new NioEventLoopGroup(workerThread);

		Bootstrap b = new Bootstrap();

		b.group(workerGroup);
		b.option(ChannelOption.TCP_NODELAY, true);
		b.handler(new LoggingHandler(LogLevel.INFO));

		b.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast(new TimeClientHandler());
			}
		});

		try {
			f = b.connect(host, port).sync();
			if (f.isSuccess()) {
				logger.info("start {}:{}", host, port);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			shutdown();
		}
	}

	@Override
	public void shutdown() {
		try {
			if (null != f)
				f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != workerGroup) {
				workerGroup.shutdownGracefully();
			}
		}
	}

}
