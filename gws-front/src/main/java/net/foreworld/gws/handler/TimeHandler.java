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
import net.foreworld.gws.protobuf.Common;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.util.Constants;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
@Sharable
public class TimeHandler extends SimpleChannelInboundHandler<RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(TimeHandler.class);

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Value("${server.id}")
	private String server_id;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("{}:{}:{}:{}", msg.getVersion(), msg.getMethod(), msg.getSeqId(), msg.getTimestamp());

		Common.SenderProtobuf.Builder sender = Common.SenderProtobuf.newBuilder();
		sender.setSender(server_id + "::" + ctx.channel().id().asLongText());
		sender.setData(msg);

		jmsMessagingTemplate.convertAndSend(Constants.PLUGIN + msg.getMethod(), sender.build().toByteArray());
	}

}
