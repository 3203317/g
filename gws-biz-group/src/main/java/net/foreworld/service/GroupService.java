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
public interface GroupService extends IService {

	/**
	 * 搜索群组并加入
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_type
	 * @return
	 */
	ResultMap<Void> search(String server_id, String channel_id, String group_type);

	/**
	 * 进入指定群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<Void> entry(String server_id, String channel_id, String group_id);

	ResultMap<List<Receiver<User>>> quit(String server_id, String channel_id);

}