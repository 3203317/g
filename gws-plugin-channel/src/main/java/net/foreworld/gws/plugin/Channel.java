package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

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

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	private static final Logger logger = LoggerFactory.getLogger(Channel.class);

	@JmsListener(destination = "${queue.channel.open}")
	public void open(TextMessage msg) {

		try {

			String token = msg.getText();
			String[] text = token.split("::");

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(1);
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(
					queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.close}")
	public void close(TextMessage msg) {

		try {
			logger.info("channel amq close: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}