package net.foreworld.gws.amq;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.model.User;

/**
 * 
 * @author huangxin
 *
 */
@Component
public class Consumer {

	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "channel.send")
	public void channel_send(BytesMessage text) {

		try {
			int len = (int) text.getBodyLength();
			byte[] data = new byte[len];
			text.readBytes(data);

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
}