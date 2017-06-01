package net.foreworld.gws.amq;

import java.util.UUID;

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
import net.foreworld.gws.protobuf.User;
import net.foreworld.gws.protobuf.method.user.Login;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:protocol.properties")
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
			logger.info("sender: {}", sender.getSender());

			Common.RequestProtobuf method = Common.RequestProtobuf.parseFrom(sender.getData());
			logger.info("{}:{}:{}:{}", method.getVersion(), method.getMethod(), method.getSeqId(),
					method.getTimestamp());

			Common.ResponseProtobuf.Builder resp = Common.ResponseProtobuf.newBuilder();

			resp.setVersion(protocol_version);
			resp.setMethod(method.getMethod());
			resp.setSeqId(method.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			User.UserProtobuf.Builder user = User.UserProtobuf.newBuilder();
			user.setUserName("黄鑫");
			user.setId(UUID.randomUUID().toString());
			user.setUserPass(UUID.randomUUID().toString());

			resp.setData(user.build().toByteString());

			jmsMessagingTemplate.convertAndSend(queue_back_send + ".bbe1c450365b4bbd839d02411167cdea",
					resp.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.open}")
	public void channel_open(TextMessage msg) {

		try {

			String[] text = msg.getText().split("::");

			Common.ResponseProtobuf.Builder resp = Common.ResponseProtobuf.newBuilder();

			resp.setVersion(protocol_version);
			resp.setMethod(95);
			resp.setSeqId(1);
			resp.setTimestamp(System.currentTimeMillis());

			Login.ResponseProtobuf.Builder data = Login.ResponseProtobuf.newBuilder();
			data.setToken(text[1]);

			resp.setData(data.build().toByteString());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], resp.build().toByteArray());

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