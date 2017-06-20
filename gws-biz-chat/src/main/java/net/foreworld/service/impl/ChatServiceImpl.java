package net.foreworld.service.impl;

import net.foreworld.model.ChatMsg;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
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
			String channel_id, Long timestamp, String receiver, String msg) {

		ResultMap<Receiver<ChatMsg>> map = new ResultMap<Receiver<ChatMsg>>();
		map.setSuccess(true);
		return map;
	}

}
