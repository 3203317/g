package net.foreworld.gws.handler;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.model.User;

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

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("{}:{}", msg.getSeqId(), msg.getTimestamp());

		try {
			User.UserProtobuf _user = User.UserProtobuf.parseFrom(msg.getData());
			logger.info("{}:{}", _user.getUserName(), _user.getUserPass());
		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		}

		Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

		resp.setVersion(msg.getVersion());
		resp.setMethod(msg.getMethod());
		resp.setSeqId(msg.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
		user.setUserName("黄鑫");
		user.setId(UUID.randomUUID().toString());
		user.setUserPass(UUID.randomUUID().toString());

		resp.setData(user.build().toByteString());

		ctx.writeAndFlush(resp);

		jmsMessagingTemplate.convertAndSend(queue_channel_send, ctx.channel().id().asLongText() + ":" + new Date());

	}

}
