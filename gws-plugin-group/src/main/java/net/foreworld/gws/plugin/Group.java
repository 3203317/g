package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.gws.protobuf.Group.GroupEntryProtobuf;
import net.foreworld.gws.protobuf.Group.GroupProtobuf;
import net.foreworld.gws.protobuf.Group.GroupSearchProtobuf;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;

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
public class Group {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource
	private GroupService groupService;

	private static final Logger logger = LoggerFactory.getLogger(Group.class);

	@JmsListener(destination = "qq3203317.3001")
	public void search(BytesMessage msg) {

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			SenderProtobuf sender = SenderProtobuf.parseFrom(data);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			RequestProtobuf req = sender.getData();
			logger.info("{}:{}:{}:{}", req.getVersion(), req.getMethod(),
					req.getSeqId(), req.getTimestamp());

			GroupSearchProtobuf chatSend = GroupSearchProtobuf.parseFrom(req
					.getData());
			logger.info("{}", chatSend.getGroupType());

			GroupProtobuf.Builder ch = GroupProtobuf.newBuilder();
			ch.setId("123321");

			ResultMap<String> map = groupService.search("user_id",
					"group_type");
			logger.info("{}:{}", map.getSuccess(), map.getData());

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(req.getMethod());
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());
			resp.setData(ch.build().toByteString());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(
					queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3002")
	public void entry(BytesMessage msg) {

		try {
			int len = (int) msg.getBodyLength();
			byte[] data = new byte[len];
			msg.readBytes(data);

			SenderProtobuf sender = SenderProtobuf.parseFrom(data);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			RequestProtobuf req = sender.getData();
			logger.info("{}:{}:{}:{}", req.getVersion(), req.getMethod(),
					req.getSeqId(), req.getTimestamp());

			GroupEntryProtobuf chatSend = GroupEntryProtobuf.parseFrom(req
					.getData());
			logger.info("{}", chatSend.getGroupId());

			GroupProtobuf.Builder ch = GroupProtobuf.newBuilder();
			ch.setId("123321");

			ResultMap<String> map = groupService.entry("user_id",
					 "group_id");
			logger.info("{}:{}", map.getSuccess(), map.getData());

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(req.getMethod());
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());
			resp.setData(ch.build().toByteString());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(
					queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}