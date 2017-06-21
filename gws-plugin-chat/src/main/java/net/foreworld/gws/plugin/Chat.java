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
import net.foreworld.gws.protobuf.Chat.ChatSendProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.model.ChatMsg;
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

			ChatSendProtobuf chatSend = ChatSendProtobuf.parseFrom(req.getData());

			ResultMap<Receiver<ChatMsg>> map = chatService.send(sender.getServerId(), sender.getChannelId(),
					chatSend.getReceiver(), chatSend.getComment());

			if (!map.getSuccess()) {
				return;
			}

			// 一对一发送
			Receiver<ChatMsg> receiver = map.getData();
			ChatMsg cm = receiver.getData();

			ChatProtobuf.Builder _cb = ChatProtobuf.newBuilder();
			_cb.setComment(cm.getComment());
			_cb.setId(cm.getId());
			_cb.setTimestamp(cm.getCreate_time());
			_cb.setSender(cm.getSender());
			_cb.setReceiver(cm.getReceiver());

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(2004);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());
			resp.setData(_cb.build().toByteString());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(receiver.getChannel_id());
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + receiver.getServer_id(),
					rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.2002")
	public void sendGroup(BytesMessage msg) {

	}

	@JmsListener(destination = "qq3203317.2003")
	public void sendAll(BytesMessage msg) {

	}

}