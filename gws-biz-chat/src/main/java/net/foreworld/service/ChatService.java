package net.foreworld.service;

import net.foreworld.model.ChatMsg;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;

/**
 *
 * @author huangxin
 *
 */
public interface ChatService extends IService {

	/**
	 * 1v1
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param chatMsg
	 * @return
	 */
	ResultMap<Receiver<ChatMsg>> send(String server_id, String channel_id, ChatMsg chatMsg);

}