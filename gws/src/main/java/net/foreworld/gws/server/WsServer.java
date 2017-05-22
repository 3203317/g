package net.foreworld.gws.server;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.foreworld.gws.initializer.WsInitializer;
import net.foreworld.util.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:server.properties")
@Component
public class WsServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(WsServer.class);

	@Value("${server.port:1234}")
	private int port;
	@Value("${server.bossThread:2}")
	private int bossThread;
	@Value("${server.workerThread:8}")
	private int workerThread;
	@Value("${server.so.backlog:1024}")
	private int so_backlog;

	@Value("${server.id}")
	private String server_id;

	@Resource(name = "wsInitializer")
	private WsInitializer wsInitializer;

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

		b.option(ChannelOption.SO_BACKLOG, so_backlog);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.option(ChannelOption.TCP_NODELAY, true);

		// b.handler(new LoggingHandler(LogLevel.INFO));

		b.childHandler(wsInitializer);

		try {
			f = b.bind().sync();
			if (f.isSuccess()) {
				logger.info("start ws {}", port);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					shutdown();
					afterStop();
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

	private void afterStop() {
		clearRedis();
	}

	/**
	 * 1. 重置服务器的连接数为 0 <br/>
	 * 2. 设置服务器的状态为 stop
	 */
	private void clearRedis() {

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return;

		j.close();
	}

}
