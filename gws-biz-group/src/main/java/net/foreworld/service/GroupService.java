package net.foreworld.service;

import net.foreworld.model.ResultMap;

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

	/**
	 * 
	 * <p>
	 * 退出群组
	 * </p>
	 *
	 * 正常退出<br/>
	 * 强制退出<br/>
	 * 
	 * @param server_id
	 * @param channel_id
	 * @return
	 */
	ResultMap<Void> quit(String server_id, String channel_id);

}