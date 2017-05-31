package net.foreworld.gws.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.Sender;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
@Sharable
public class TimeHandler extends SimpleChannelInboundHandler<Method.RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(TimeHandler.class);

	@Value("${queue.channel.send}")
	private String queue_channel_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Value("${server.id}")
	private String server_id;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("{}:{}", msg.getSeqId(), msg.getTimestamp());

		Sender.SenderProtobuf.Builder sender = Sender.SenderProtobuf.newBuilder();
		sender.setSender(server_id + "::" + ctx.channel().id().asLongText());
		sender.setMethod(msg.toByteString());

		jmsMessagingTemplate.convertAndSend(queue_channel_send, sender.build().toByteArray());
	}

}
