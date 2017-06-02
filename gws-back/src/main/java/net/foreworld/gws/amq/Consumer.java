package net.foreworld.gws.amq;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Common;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Consumer {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "${queue.channel.send}")
	public void channel_send(BytesMessage msg) {

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			Common.SenderProtobuf sender = Common.SenderProtobuf.parseFrom(data);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			Common.RequestProtobuf method = sender.getData();
			logger.info("{}:{}:{}:{}", method.getVersion(), method.getMethod(), method.getSeqId(),
					method.getTimestamp());

			Common.ResponseProtobuf.Builder resp = Common.ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(method.getMethod());
			resp.setSeqId(method.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			Common.ReceiverProtobuf.Builder re = Common.ReceiverProtobuf.newBuilder();
			re.setReceiver(text[1]);
			re.setData(resp);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], re.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.open}")
	public void channel_open(TextMessage msg) {

		try {

			String token = msg.getText();
			String[] text = token.split("::");

			Common.ResponseProtobuf.Builder resp = Common.ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(95);
			resp.setSeqId(1);
			resp.setTimestamp(System.currentTimeMillis());

			Common.ReceiverProtobuf.Builder receiver = Common.ReceiverProtobuf.newBuilder();
			receiver.setReceiver(text[1]);
			receiver.setData(resp);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], receiver.build().toByteArray());

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.close}")
	public void channel_close(TextMessage msg) {

		try {
			logger.info("channel amq close: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.front.start}")
	public void front_start(TextMessage msg) {

		try {
			logger.info("front amq start: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.front.stop}")
	public void front_stop(TextMessage msg) {

		try {
			logger.info("front amq stop: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}
}