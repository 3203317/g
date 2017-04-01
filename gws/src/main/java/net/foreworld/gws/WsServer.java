package net.foreworld.gws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.foreworld.gws.handler.TimeServerHandler;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class WsServer extends Server {

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

		b.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast(new TimeServerHandler());
			}
		});

		try {
			ChannelFuture f = b.bind().sync();
			if (f.isSuccess()) {
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
		// TODO Auto-generated method stub

	}

}
