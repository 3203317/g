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
	 * @param user_id
	 * @return
	 */
	ResultMap<String> logout(String user_id);

}