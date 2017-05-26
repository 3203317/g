package net.foreworld.gws.amq;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.util.ChannelUtil;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Consumer {

	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "${queue.back.send}")
	public void back_send(BytesMessage msg) {

		if (!(msg instanceof BytesMessage)) {
			logger.error("not convert");
			return;
		}

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			Method.ResponseProtobuf b = Method.ResponseProtobuf.parseFrom(data);
			ChannelUtil.getDefault().broadcast(b);

		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}