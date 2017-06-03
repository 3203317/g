package net.foreworld.gws.initializer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import net.foreworld.gws.codec.BinaryBuildEncode;
import net.foreworld.gws.codec.BinaryDecode;
import net.foreworld.gws.handler.BlacklistHandler;
import net.foreworld.gws.handler.ExceptionHandler;
import net.foreworld.gws.handler.HeartbeatHandler;
import net.foreworld.gws.handler.LoginHandler;
import net.foreworld.gws.handler.ProtocolSafeHandler;
import net.foreworld.gws.handler.TimeHandler;
import net.foreworld.gws.handler.TimeVersionHandler;
import net.foreworld.gws.handler.TimeoutHandler;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;

/**
 *
 * @author huangxin
 *
 */
@Component
public class WsInitializer extends ChannelInitializer<NioSocketChannel> {

	@Value("${server.idle.readerIdleTime:3}")
	private int readerIdleTime;

	@Value("${server.idle.writerIdleTime:7}")
	private int writerIdleTime;

	@Value("${server.idle.allIdleTime:10}")
	private int allIdleTime;

	@Resource(name = "blacklistHandler")
	private BlacklistHandler blacklistHandler;

	@Resource(name = "binaryBuildEncode")
	private BinaryBuildEncode binaryBuildEncode;

	@Resource(name = "binaryDecode")
	private BinaryDecode binaryDecode;

	@Resource(name = "timeoutHandler")
	private TimeoutHandler timeoutHandler;

	@Resource(name = "loginHandler")
	private LoginHandler loginHandler;

	@Resource(name = "protocolSafeHandler")
	private ProtocolSafeHandler protocolSafeHandler;

	@Resource(name = "timeVersionHandler")
	private TimeVersionHandler timeVersionHandler;

	@Resource(name = "timeHandler")
	private TimeHandler timeHandler;

	@Resource(name = "exceptionHandler")
	private ExceptionHandler exceptionHandler;

	@Resource(name = "heartbeatHandler")
	private HeartbeatHandler heartbeatHandler;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ChannelPipeline pipe = ch.pipeline();

		pipe.addLast(exceptionHandler);
		pipe.addLast(blacklistHandler);

		pipe.addLast(new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.SECONDS));
		pipe.addLast(timeoutHandler);

		pipe.addLast(new HttpServerCodec());
		pipe.addLast(new HttpObjectAggregator(1024 * 64));
		pipe.addLast(new ChunkedWriteHandler());
		pipe.addLast(new HttpContentCompressor());
		pipe.addLast(protocolSafeHandler);
		pipe.addLast(new WebSocketServerProtocolHandler("/", null, true));

		pipe.addLast(new WebSocketServerCompressionHandler());

		pipe.addLast(binaryDecode);

		// pipe.addLast(new ProtobufVarint32FrameDecoder());
		pipe.addLast(new ProtobufDecoder(RequestProtobuf.getDefaultInstance()));
		pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
		// pipe.addLast(new ProtobufEncoder());

		pipe.addLast(binaryBuildEncode);

		pipe.addLast(timeVersionHandler);
		pipe.addLast(loginHandler);
		pipe.addLast(heartbeatHandler);

		pipe.addLast(timeHandler);
	}

}
