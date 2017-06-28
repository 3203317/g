package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

import net.foreworld.gws.protobuf.Chat.ChatMsgProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.model.ChatMsg;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ChatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

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

			ChatMsgProtobuf cmp = ChatMsgProtobuf.parseFrom(req.getData());

			ChatMsg chatMsg = new ChatMsg();
			chatMsg.setReceiver(cmp.getReceiver());
			chatMsg.setMessage(cmp.getMessage());
			chatMsg.setExtend_data(cmp.getExtendData());

			ResultMap<Receiver<ChatMsg>> map = chatService.send(
					sender.getServerId(), sender.getChannelId(), chatMsg);

			if (!map.getSuccess()) {
				return;
			}

			// 1v1 send
			Receiver<ChatMsg> _receiver = map.getData();

			ChatMsg _chatMsg = _receiver.getData();

			ChatMsgProtobuf.Builder _cmpb = ChatMsgProtobuf.newBuilder();
			_cmpb.setId(_chatMsg.getId());
			_cmpb.setSender(_chatMsg.getSender());
			_cmpb.setReceiver(_chatMsg.getReceiver());
			_cmpb.setMessage(_chatMsg.getMessage());

			if (null != _chatMsg.getExtend_data()) {
				_cmpb.setExtendData(_chatMsg.getExtend_data());
			}

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(2002);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());
			resp.setData(_cmpb.build().toByteString());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setData(resp);
			rec.setReceiver(_receiver.getChannel_id());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "."
					+ _receiver.getServer_id(), rec.build().toByteArray());

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