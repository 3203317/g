package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.model.ResultMap;
import net.foreworld.service.UserService;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Channel {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Value("${queue.group.quit}")
	private String queue_group_quit;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(Channel.class);

	@JmsListener(destination = "${queue.channel.open}")
	public void open(TextMessage msg) {

		try {

			String[] text = msg.getText().split("::");

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(1);
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setData(resp);
			rec.setReceiver(text[1]);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.close}")
	public void close(TextMessage msg) {

		try {

			String[] text = msg.getText().split("::");
			quit(text[0], text[1]);

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	private void quit(String server_id, String channel_id) {

		// 执行业务层用户退出操作
		ResultMap<Void> map = userService.logout(server_id, channel_id);
		logger.info("{}:{}", map.getSuccess(), map.getCode());

		if (!map.getSuccess()) {
			return;
		}

		jmsMessagingTemplate.convertAndSend(queue_group_quit, server_id + "::" + channel_id);
	}

}