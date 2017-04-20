package net.foreworld.gws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.foreworld.gws.initializer.WsInitializer;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:server.properties")
@Component
public class TcpServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

	@Value("${server.port:1234}")
	private int port;
	@Value("${server.bossThread:2}")
	private int bossThread;
	@Value("${server.workerThread:8}")
	private int workerThread;

	@Autowired
	private WsInitializer wsInitializer;

	public TcpServer() {

	}

	public TcpServer(int port) {
		this.port = port;
	}

	public TcpServer(int port, int bossThread, int workerThread) {
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

		b.option(ChannelOption.SO_BACKLOG, 2);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.option(ChannelOption.TCP_NODELAY, false);

		b.handler(new LoggingHandler(LogLevel.INFO));

		b.childHandler(wsInitializer);

		try {
			f = b.bind().sync();
			if (f.isSuccess()) {
				logger.info("start {}", port);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					shutdown();
				}
			});
		}
	}

	@Override
	public void shutdown() {
		if (null != f) {
			f.channel().close().syncUninterruptibly();
		}
		if (null != bossGroup) {
			bossGroup.shutdownGracefully();
		}
		if (null != workerGroup) {
			workerGroup.shutdownGracefully();
		}
	}

}
