package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;

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
	ResultMap<List<Receiver<String>>> search(String server_id, String channel_id, String group_type);

	/**
	 * 进入指定群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<List<Receiver<String>>> entry(String server_id, String channel_id, String group_id);

	/**
	 * 退出所有群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @return
	 */
	ResultMap<List<Receiver<String>>> quit(String server_id, String channel_id);

	/**
	 * 退出指定群组
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<List<Receiver<String>>> quit(String server_id, String channel_id, String group_id);

	/**
	 * 旁观者
	 * 
	 * @param server_id
	 * @param channel_id
	 * @param group_id
	 * @return
	 */
	ResultMap<List<Receiver<String>>> visit(String server_id, String channel_id, String group_id);

}