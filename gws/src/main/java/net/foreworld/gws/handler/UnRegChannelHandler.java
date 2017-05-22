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
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.util.ChannelUtil;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@PropertySource("classpath:server.properties")
@Component
@Sharable
public class UnRegChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UnRegChannelHandler.class);

	@Value("${server.id}")
	private String server_id;

	@Value("${dest.channel.close}")
	private String dest_channel_close;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("", cause);
		ctx.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		closeChannel(ctx.channel().id().asLongText());
		super.channelUnregistered(ctx);
	}

	/**
	 * 删除通道
	 * 
	 * @param channel_id
	 */
	private void closeChannel(String channel_id) {
		ChannelUtil.getDefault().removeChannel(channel_id);

		jmsMessagingTemplate.convertAndSend(dest_channel_close, server_id + ":" + channel_id);
		logger.info("channel close {}:{}", server_id, channel_id);
	}
}
