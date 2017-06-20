package net.foreworld.gws.plugin;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import net.foreworld.gws.protobuf.Common.ErrorProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.gws.protobuf.User.UserProtobuf;
import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Channel {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(Channel.class);

	@JmsListener(destination = "${queue.channel.open}")
	public void open(TextMessage msg) {

		try {

			String[] text = msg.getText().split("::");

			ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
			resp.setVersion(protocol_version);
			resp.setSeqId(1);
			resp.setTimestamp(System.currentTimeMillis());

			ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
			rec.setReceiver(text[1]);
			rec.setData(resp);

			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + text[0], rec.build().toByteArray());

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.channel.close}")
	public void close(TextMessage msg) {

		try {

			String[] text = msg.getText().split("::");

			quit(text[0], text[1]);

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	private void quit(String server_id, String channel_id) {

		ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
		resp.setVersion(protocol_version);
		resp.setTimestamp(System.currentTimeMillis());

		ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();

		// 执行业务层用户退出操作
		ResultMap<List<net.foreworld.model.Channel<User>>> map = userService.logout(server_id, channel_id);
		logger.info("{}:{}", map.getSuccess(), map.getMsg());

		if (!map.getSuccess()) {

			ErrorProtobuf.Builder err = ErrorProtobuf.newBuilder();
			err.setMsg(map.getMsg());

			resp.setError(err);
			rec.setData(resp);

			rec.setReceiver(channel_id);
			jmsMessagingTemplate.convertAndSend(queue_back_send + "." + server_id, rec.build().toByteArray());
			return;
		}

		// 给用户自己发送一条成功消息
		rec.setData(resp);

		rec.setReceiver(channel_id);
		jmsMessagingTemplate.convertAndSend(queue_back_send + "." + server_id, rec.build().toByteArray());

		// 给同组其他成员分别发送一条退出消息
		resp.setMethod(3004);

		List<net.foreworld.model.Channel<User>> list = map.getData();

		for (int i = 0, j = list.size(); i < j; i++) {
			net.foreworld.model.Channel<User> channel = list.get(i);

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