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

import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.method.user.Login;
import net.foreworld.gws.protobuf.model.User;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Consumer {

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

			Method.RequestProtobuf method = Method.RequestProtobuf.parseFrom(data);
			logger.info("{}:{}:{}:{}", method.getVersion(), method.getMethod(), method.getSeqId(),
					method.getTimestamp());

			User.UserProtobuf user = User.UserProtobuf.parseFrom(method.getData());
			logger.info("{}:{}", user.getUserName(), user.getUserPass());

		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.open}")
	public void channel_open(TextMessage msg) {

		try {
			logger.info(msg.getText());

			Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

			resp.setVersion(1);
			resp.setMethod(2);
			resp.setSeqId(3);
			resp.setTimestamp(System.currentTimeMillis());

			Login.ResponseProtobuf.Builder data = Login.ResponseProtobuf.newBuilder();
			data.setToken("token");

			resp.setData(data.build().toByteString());

			jmsMessagingTemplate.convertAndSend(queue_back_send, resp.build().toByteArray());

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.front.start}")
	public void front_start(TextMessage msg) {

		try {
			logger.info(msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.front.stop}")
	public void front_stop(TextMessage msg) {

		try {
			logger.info(msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}
}