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

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Common.DataProtobuf;
import net.foreworld.gws.protobuf.Common.ErrorProtobuf;
import net.foreworld.gws.protobuf.Common.PrimaryKeyProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.Common.SenderProtobuf;
import net.foreworld.gws.protobuf.Group.GroupSearchProtobuf;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.GroupService;

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
			if (!quit(sender)) {
				return;
			}

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			String group_type = GroupSearchProtobuf.parseFrom(req.getData()).getGroupType();

			ResultMap<SameData<String>> map = groupService.search(sender.getServerId(), sender.getChannelId(),
					group_type);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			// 给相关人员发送一条进入群组消息

			SameData<String> _data = map.getData();

			if (null == _data) {
				return;
			}

			List<Receiver<Void>> _list = _data.getReceivers();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
			_dpb.setBody(_data.getData());

			resp.setMethod(3002);
			resp.setData(_dpb.build().toByteString());

			rec.setData(resp);

			for (int i = 0; i < j; i++) {
				Receiver<Void> _receiver = _list.get(i);

				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3003")
	public void entry(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);
			if (!quit(sender)) {
				return;
			}

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			String group_id = PrimaryKeyProtobuf.parseFrom(req.getData()).getId();

			ResultMap<SameData<String>> map = groupService.entry(sender.getServerId(), sender.getChannelId(), group_id);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			// 给相关人员发送一条进入群组消息

			SameData<String> _data = map.getData();

			if (null == _data) {
				return;
			}

			List<Receiver<Void>> _list = _data.getReceivers();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
			_dpb.setBody(_data.getData());

			resp.setMethod(3004);
			resp.setData(_dpb.build().toByteString());

			rec.setData(resp);

			for (int i = 0; i < j; i++) {
				Receiver<Void> _receiver = _list.get(i);

				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3005")
	public void quit(BytesMessage msg) {

		try {

			quit(read(msg));

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	private boolean quit(SenderProtobuf sender) {

		RequestProtobuf req = sender.getData();

		ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
		resp.setVersion(protocol_version);
		resp.setSeqId(req.getSeqId());
		resp.setTimestamp(System.currentTimeMillis());

		ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

		// 执行业务层用户退出群组操作
		ResultMap<SameData<String>> map = groupService.quit(sender.getServerId(), sender.getChannelId());
		logger.info("{}:{}", map.getSuccess(), map.getMsg());

		if (!map.getSuccess()) {

			ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
			err.setCode(map.getCode());
			err.setMsg(map.getMsg());

			resp.setMethod(req.getMethod());
			resp.setError(err);

			rec.setData(resp);
			rec.setReceiver(sender.getChannelId());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
					rec.build().toByteArray());
			return false;
		}

		SameData<String> _data = map.getData();

		if (null == _data) {
			return true;
		}

		List<Receiver<Void>> _list = _data.getReceivers();

		if (null == _list) {
			return true;
		}

		int j = _list.size();

		if (0 == j) {
			return true;
		}

		// 给相关人员发送一条退出群组消息

		DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
		_dpb.setBody(_data.getData());

		resp.setMethod(3006);
		resp.setData(_dpb.build().toByteString());

		rec.setData(resp);

		for (int i = 0; i < j; i++) {
			Receiver<Void> _receiver = _list.get(i);

			rec.setReceiver(_receiver.getChannel_id());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
					rec.build().toByteArray());
		}

		return true;
	}

	@JmsListener(destination = "qq3203317.3007")
	public void visit(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);
			if (!quit(sender)) {
				return;
			}

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			String group_id = PrimaryKeyProtobuf.parseFrom(req.getData()).getId();

			ResultMap<SameData<String>> map = groupService.visit(sender.getServerId(), sender.getChannelId(), group_id);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			// 给相关人员发送一条进入群组消息

			SameData<String> _data = map.getData();

			if (null == _data) {
				return;
			}

			List<Receiver<Void>> _list = _data.getReceivers();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
			_dpb.setBody(_data.getData());

			resp.setMethod(3008);
			resp.setData(_dpb.build().toByteString());

			rec.setData(resp);

			for (int i = 0; i < j; i++) {
				Receiver<Void> _receiver = _list.get(i);

				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "qq3203317.3009")
	public void participant(BytesMessage msg) {

		try {

			SenderProtobuf sender = read(msg);
			if (!quit(sender)) {
				return;
			}

			RequestProtobuf req = sender.getData();

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(req.getSeqId());
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

			String group_id = PrimaryKeyProtobuf.parseFrom(req.getData()).getId();

			ResultMap<SameData<String>> map = groupService.participant(sender.getServerId(), sender.getChannelId(),
					group_id);
			logger.info("{}:{}", map.getSuccess(), map.getMsg());

			if (!map.getSuccess()) {

				ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
				err.setCode(map.getCode());
				err.setMsg(map.getMsg());

				resp.setMethod(req.getMethod());
				resp.setError(err);

				rec.setData(resp);
				rec.setReceiver(sender.getChannelId());

				// 消息回传给自己
				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + sender.getServerId(),
						rec.build().toByteArray());
				return;
			}

			// 给相关人员发送一条进入群组消息

			SameData<String> _data = map.getData();

			if (null == _data) {
				return;
			}

			List<Receiver<Void>> _list = _data.getReceivers();

			if (null == _list) {
				return;
			}

			int j = _list.size();

			if (0 == j) {
				return;
			}

			DataProtobuf.Builder _dpb = DataProtobuf.newBuilder();
			_dpb.setBody(_data.getData());

			resp.setMethod(3010);
			resp.setData(_dpb.build().toByteString());

			rec.setData(resp);

			for (int i = 0; i < j; i++) {
				Receiver<Void> _receiver = _list.get(i);

				rec.setReceiver(_receiver.getChannel_id());

				jmsMessagingTemplate.convertAndSend(queue_back_send + "." + _receiver.getServer_id(),
						rec.build().toByteArray());
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}