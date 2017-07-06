package net.foreworld.gws.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.model.ProtocolModel;
import net.foreworld.gws.util.Constants;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
@Sharable
public class TimeV2Handler extends SimpleChannelInboundHandler<ProtocolModel> {

	private static final Logger logger = LoggerFactory.getLogger(TimeV2Handler.class);

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Value("${server.id}")
	private String server_id;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel msg) throws Exception {
		logger.info("{}:{}:{}:{}", msg.getVersion(), msg.getMethod(), msg.getSeqId(), msg.getTimestamp());

		msg.setServerId(server_id);
		msg.setChannelId(ctx.channel().id().asLongText());

		Gson gson = new Gson();

		jmsMessagingTemplate.convertAndSend(Constants.PLUGIN + msg.getMethod(), gson.toJson(msg));
		ctx.flush();
	}

}
