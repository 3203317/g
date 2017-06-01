package net.foreworld.gws.initializer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import net.foreworld.gws.codec.MsgCodec;
import net.foreworld.gws.handler.EchoHandler;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;

/**
 *
 * @author huangxin
 *
 */
@Component
public class TcpInitializer extends ChannelInitializer<NioSocketChannel> {

	@Resource(name = "echoHandler")
	private EchoHandler echoHandler;

	@Resource(name = "msgCodec")
	private MsgCodec msgCodec;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		pipe.addLast(new ProtobufVarint32FrameDecoder());
		pipe.addLast(new ProtobufDecoder(RequestProtobuf.getDefaultInstance()));
		pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
		pipe.addLast(new ProtobufEncoder());

		pipe.addLast(msgCodec);
		pipe.addLast(echoHandler);
	}

}
