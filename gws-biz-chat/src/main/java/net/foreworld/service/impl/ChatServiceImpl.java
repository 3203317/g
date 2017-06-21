package net.foreworld.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.ChatMsg;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ChatService;

/**
 *
 * @author huangxin
 *
 */
@Service("chatService")
public class ChatServiceImpl extends BaseService implements ChatService {

	private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

	@Override
	public ResultMap<Receiver<ChatMsg>> send(String server_id, String channel_id, String receiver, String comment) {

		ResultMap<Receiver<ChatMsg>> map = new ResultMap<Receiver<ChatMsg>>();
		map.setSuccess(true);
		return map;
	}

}