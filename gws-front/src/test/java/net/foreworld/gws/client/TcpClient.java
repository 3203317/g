package net.foreworld.gws.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.Resource;

import net.foreworld.gws.client.handler.LoginHandler;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.util.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:server.properties")
@Component
public class TcpClient extends Client {

	private static final Logger logger = LoggerFactory
			.getLogger(TcpClient.class);

	@Value("${server.port:1234}")
	private int port;
	@Value("${server.host:127.0.0.1}")
	private String host;

	public TcpClient() {

	}

	private ChannelFuture f;
	private EventLoopGroup workerGroup;

	@Resource(name = "loginHandler")
	private LoginHandler loginHandler;

	@Override
	public void start() {

		workerGroup = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();

		b.group(workerGroup);
		b.channel(NioSocketChannel.class);

		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.option(ChannelOption.TCP_NODELAY, true);

		b.handler(new LoggingHandler(LogLevel.INFO));

		b.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();

				pipe.addLast(new ProtobufVarint32FrameDecoder());
				pipe.addLast(new ProtobufDecoder(ResponseProtobuf
						.getDefaultInstance()));
				pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
				pipe.addLast(new ProtobufEncoder());

				pipe.addLast(loginHandler);
			}
		});

		try {
			f = b.connect(host, port).sync();
			if (f.isSuccess()) {
				logger.info("connect tcp {}:{}", host, port);
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
		if (null != workerGroup) {
			workerGroup.shutdownGracefully();
		}
	}

}
