package net.foreworld.service;

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
	 * @param receiver
	 * @param comment
	 * @return
	 */
	ResultMap<Receiver<String>> send(String server_id, String channel_id,
			String receiver, String comment);

}