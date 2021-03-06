package net.foreworld.service.impl;

import net.foreworld.model.ChatMsg;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.ChatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author huangxin
 *
 */
@Service("chatService")
public class ChatServiceImpl extends BaseService implements ChatService {

	private static final Logger logger = LoggerFactory
			.getLogger(ChatServiceImpl.class);

	@Override
	public ResultMap<Receiver<ChatMsg>> send(String server_id,
			String channel_id, ChatMsg chatMsg) {

		ResultMap<Receiver<ChatMsg>> map = new ResultMap<Receiver<ChatMsg>>();
		map.setSuccess(false);

		chatMsg.setId("id");
		chatMsg.setSender("sender");

		Receiver<ChatMsg> rec = new Receiver<ChatMsg>();
		rec.setServer_id(server_id);
		rec.setChannel_id(channel_id);
		rec.setData(chatMsg);

		map.setData(rec);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<SameData<ChatMsg>> sendGroup(String server_id,
			String channel_id, ChatMsg chatMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<SameData<ChatMsg>> sendAll(String server_id,
			String channel_id, ChatMsg chatMsg) {
		// TODO Auto-generated method stub
		return null;
	}

}
