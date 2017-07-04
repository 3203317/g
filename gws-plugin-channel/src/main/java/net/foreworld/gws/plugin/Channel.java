package net.foreworld.gws.plugin;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import net.foreworld.gws.protobuf.Common.DataProtobuf;
import net.foreworld.gws.protobuf.Common.ReceiverProtobuf;
import net.foreworld.gws.protobuf.Common.ResponseProtobuf;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

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
			rec.setData(resp);
			rec.setReceiver(text[1]);

			jmsMessagingTemplate.convertAndSend(
					queue_back_send + "." + text[0], rec.build().toByteArray());

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

		// 执行业务层用户退出操作
		ResultMap<SameData<String>> map = userService.logout(server_id,
				channel_id);
		logger.info("{}:{}", map.getSuccess(), map.getCode());

		if (!map.getSuccess()) {
			return;
		}

		// 给相关人员发送一条退出消息

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

		ResponseProtobuf.Builder resp = ResponseProtobuf.newBuilder();
		resp.setVersion(protocol_version);
		resp.setMethod(0);
		resp.setTimestamp(System.currentTimeMillis());
		resp.setData(_dpb.build().toByteString());

		ReceiverProtobuf.Builder rec = ReceiverProtobuf.newBuilder();
		rec.setData(resp);

		for (int i = 0; i < j; i++) {
			Receiver<Void> _receiver = _list.get(i);

			rec.setReceiver(_receiver.getChannel_id());

			jmsMessagingTemplate.convertAndSend(queue_back_send + "."
					+ _receiver.getServer_id(), rec.build().toByteArray());
		}
	}

}