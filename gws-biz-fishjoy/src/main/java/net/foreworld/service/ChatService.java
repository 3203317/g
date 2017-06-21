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
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param receiver
	 * @param comment
	 * @return
	 */
	ResultMap<Receiver<ChatMsg>> send(String server_id, String channel_id, String receiver, String comment);

}