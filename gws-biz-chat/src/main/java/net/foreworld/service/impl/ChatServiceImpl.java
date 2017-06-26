package net.foreworld.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	public ResultMap<Receiver<String>> send(String server_id, String channel_id, String receiver, String comment) {

		ResultMap<Receiver<String>> map = new ResultMap<Receiver<String>>();
		map.setSuccess(false);

		Receiver<String> rec = new Receiver<String>();
		rec.setServer_id(server_id);
		rec.setChannel_id(channel_id);
		rec.setData(comment);

		map.setData(rec);
		map.setSuccess(true);
		return map;
	}

}
