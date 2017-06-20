package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.User;

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
	ResultMap<List<Receiver<User>>> logout(String server_id, String channel_id);

}