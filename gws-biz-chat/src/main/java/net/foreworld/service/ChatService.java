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
	 * @param timestamp
	 *            发送时间
	 * @param receiver
	 *            接收方：用户id
	 * @param msg
	 *            消息
	 * @return
	 */
	ResultMap<Receiver<ChatMsg>> send(String server_id, String channel_id,
			Long timestamp, String receiver, String msg);

}