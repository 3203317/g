package net.foreworld.gws.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import net.foreworld.gws.handler.TimeHandler;
import net.foreworld.gws.protobuf.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		pipe.addLast(new ProtobufVarint32FrameDecoder());
		pipe.addLast(new ProtobufDecoder(Login.LoginRequest
				.getDefaultInstance()));
		pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
		pipe.addLast(new ProtobufEncoder());
	}

}
