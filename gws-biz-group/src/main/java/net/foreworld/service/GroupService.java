package net.foreworld.service;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;

/**
 *
 * @author huangxin
 *
 */
public interface GroupService extends IService {

	/**
	 * 搜索群组类型并加入
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_type
	 * @return
	 */
	ResultMap<SameData<String>> search(String server_id, String channel_id, String group_type);

	/**
	 * 进入指定群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<SameData<String>> entry(String server_id, String channel_id, String group_id);

	/**
	 * 退出所有群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @return
	 */
	ResultMap<SameData<String>> quit(String server_id, String channel_id);

	/**
	 * 退出指定群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<SameData<String>> quit(String server_id, String channel_id, String group_id);

	/**
	 * 旁观者
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<SameData<String>> visit(String server_id, String channel_id, String group_id);

	/**
	 * 参与者
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<SameData<String>> participant(String server_id, String channel_id, String group_id);

}