package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

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
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class FishJoy {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	private static final Logger logger = LoggerFactory.getLogger(FishJoy.class);

	@JmsListener(destination = "${queue.fishjoy.shot}")
	public void shot(BytesMessage msg) {

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			Common.SenderProtobuf sender = Common.SenderProtobuf.parseFrom(data);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			Common.RequestProtobuf req = sender.getData();
			logger.info("{}:{}:{}:{}", req.getVersion(), req.getMethod(), req.getSeqId(), req.getTimestamp());

			Common.ResponseProtobuf.Builder resp = Common.ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(req.getMethod());
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			Common.ReceiverProtobuf.Builder rec = Common.ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}