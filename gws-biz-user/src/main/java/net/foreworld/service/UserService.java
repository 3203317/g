package net.foreworld.service;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;

/**
 *
 * @author huangxin
 *
 */
public interface UserService extends IService {

	/**
	 *
	 * @param server_id
	 * @param channel_id
	 * @return
	 */
	ResultMap<SameData<String>> logout(String server_id, String channel_id);

}