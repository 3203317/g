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

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Common.ErrorProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.gws.protobuf.Group.GroupEntryProtobuf;
import net.foreworld.gws.protobuf.Group.GroupProtobuf;
import net.foreworld.gws.protobuf.Group.GroupSearchProtobuf;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;
import net.foreworld.util.StringUtil;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Group extends BasePlugin {

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

			SenderProtobuf sender = read(msg);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			RequestProtobuf req = sender.getData();
			logger.debug("{}:{}:{}:{}", req.getVersion(), req.getMethod(), req.getSeqId(), req.getTimestamp());

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(req.getMethod());
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);

			ByteString _data = req.getData();

			if (_data.isEmpty()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode("invalid_data");

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

			GroupSearchProtobuf groupSearch = GroupSearchProtobuf.parseFrom(_data);
			String groupType = StringUtil.isEmpty(groupSearch.getGroupType());

			if (null == groupType) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode("invalid_grouptype");

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

			ResultMap<Void> map = groupService.search(text[0], text[1], groupType);
			logger.info("{}:{}", map.getSuccess(), map.getData());

			rec.setData(resp);
			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3002")
	public void entry(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			RequestProtobuf req = sender.getData();
			logger.info("{}:{}:{}:{}", req.getVersion(), req.getMethod(), req.getSeqId(), req.getTimestamp());

			GroupEntryProtobuf chatSend = GroupEntryProtobuf.parseFrom(req.getData());
			logger.info("{}", chatSend.getGroupId());

			GroupProtobuf.Builder ch = GroupProtobuf.newBuilder();
			ch.setId("123321");

			ResultMap<Void> map = groupService.entry(text[0], text[1], "group_id");
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

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3003")
	public void quit(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);
			String[] text = sender.getSender().split("::");
			logger.info("sender: {}", sender.getSender());

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setMethod(req.getMethod());
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);

			ResultMap<Void> map = groupService.quit(text[0], text[1]);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode("error_quit");
				err.setMsg(map.getMsg());

				resp.setError(err);

				rec.setData(resp);
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}