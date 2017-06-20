package net.foreworld.gws.plugin;

import java.util.List;

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
import net.foreworld.gws.protobuf.Group.GroupSearchProtobuf;
import net.foreworld.gws.protobuf.User.UserProtobuf;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
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
			quit(sender);

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

			String _group_type = StringUtil.isEmpty(GroupSearchProtobuf.parseFrom(_data).getGroupType());

			if (null == _group_type) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode("invalid_grouptype");

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

			ResultMap<Void> map = groupService.search(text[0], text[1], _group_type);
			logger.info("{}:{}", map.getSuccess(), map.getData());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setMsg(map.getMsg());

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

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

			quit(sender);

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

			String _group_id = StringUtil.isEmpty(GroupEntryProtobuf.parseFrom(_data).getGroupId());

			if (null == _group_id) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode("invalid_group_id");

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

			ResultMap<Void> map = groupService.entry(text[0], text[1], _group_id);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setMsg(map.getMsg());

				resp.setError(err);
				rec.setData(resp);

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
				return;
			}

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

			quit(read(msg));

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	private void quit(SenderProtobuf sender) {

		String[] text = sender.getSender().split("::");
		logger.info("sender: {}", sender.getSender());

		RequestProtobuf req = sender.getData();
		logger.debug("{}:{}:{}:{}", req.getVersion(), req.getMethod(), req.getSeqId(), req.getTimestamp());

		ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
		resp.setVersion(protocol_version);
		resp.setSeqId(req.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

		// 执行业务层用户退出群组操作
		ResultMap<List<Receiver<User>>> map = groupService.quit(text[0], text[1]);
		logger.info("{}:{}", map.getSuccess(), map.getMsg());

		if (!map.getSuccess()) {

			ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
			err.setMsg(map.getMsg());

			resp.setMethod(req.getMethod());
			resp.setError(err);
			rec.setData(resp);

			rec.setReceiver(text[1]);
			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());
			return;
		}

		// 给用户自己发送一条成功消息
		resp.setMethod(req.getMethod());
		rec.setData(resp);

		rec.setReceiver(text[1]);
		jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		// 给同组其他成员分别发送一条退出消息
		resp.setMethod(3004);

		List<Receiver<User>> list = map.getData();

		for (int i = 0, j = list.size(); i < j; i++) {
			Receiver<User> channel = list.get(i);

			User _u = channel.getData();

			UserProtobuf.Builder _ub = UserProtobuf.newBuilder();
			_ub.setId(_u.getId());
			_ub.setUserName(_u.getUser_name());
			_ub.setNickname(_u.getNickname());

			resp.setData(_ub.build().toByteString());

			rec.setData(resp);
			rec.setReceiver(channel.getChannel_id());
			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + channel.getServer_id(),
					rec.build().toByteArray());
		}
	}

}