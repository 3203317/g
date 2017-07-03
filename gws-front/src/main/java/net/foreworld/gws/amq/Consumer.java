package net.foreworld.gws.amq;

import io.netty.channel.Channel;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.util.ChannelUtil;
import net.foreworld.gws.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Consumer {

	private static final Logger logger = LoggerFactory
			.getLogger(Consumer.class);

	@JmsListener(destination = "${queue.back.send}" + "." + "${server.id}")
	public void back_send(BytesMessage msg) {

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			ReceiverProtobuf rec = ReceiverProtobuf.parseFrom(data);

			if (Constants.ALL.equals(rec.getReceiver())) {
				ChannelUtil.getDefault().broadcast(rec.getData());
				return;
			}

			Channel c = ChannelUtil.getDefault().getChannel(rec.getReceiver());

			if (null != c)
				c.writeAndFlush(rec.getData());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}