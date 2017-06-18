package net.foreworld.service;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;

/**
 *
 * @author huangxin
 *
 */
public interface UserService extends IService<User> {

	/**
	 *
	 * @param server_id
	 * @param channel_id
	 * @return
	 */
	ResultMap<Void> logout(String server_id, String channel_id);

}