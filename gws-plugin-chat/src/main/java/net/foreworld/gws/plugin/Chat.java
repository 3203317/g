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

import net.foreworld.gws.protobuf.Chat.ChatProtobuf;
import net.foreworld.gws.protobuf.Common.DataProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ChatService;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Chat extends BasePlugin {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource
	private ChatService chatService;

	private static final Logger logger = LoggerFactory.getLogger(Chat.class);

	@JmsListener(destination = "qq3203317.2001")
	public void send(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);

			RequestProtobuf req = sender.getData();

			ChatProtobuf cp = ChatProtobuf.parseFrom(req.getData());

			ResultMap<Receiver<String>> map = chatService.send(sender.getServerId(), sender.getChannelId(),
					cp.getReceiver(), cp.getComment());

			if (!map.getSuccess()) {
				return;
			}

			// 一对一发送
			Receiver<String> _receiver = map.getData();

			DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
			_dpb.setBody(_receiver.getData());

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(2002);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());
			resp.setData(_dpb.build().toByteString());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setData(resp);
			rec.setReceiver(_receiver.getChannel_id());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
					rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.2003")
	public void sendGroup(BytesMessage msg) {

	}

	@JmsListener(destination = "qq3203317.2005")
	public void sendAll(BytesMessage msg) {

	}

}