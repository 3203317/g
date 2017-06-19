package net.foreworld.service;

import net.foreworld.model.Group;
import net.foreworld.model.ResultMap;

/**
 *
 * @author huangxin
 *
 */
public interface GroupService extends IService<Group> {

	/**
	 * 搜索群组并加入
	 *
	 * @param user_id
	 * @param group_type
	 * @return
	 */
	ResultMap<String> search(String user_id, String group_type);

	/**
	 * 进入指定群组
	 *
	 * @param user_id
	 * @param group_id
	 * @return
	 */
	ResultMap<String> entry(String user_id, String group_id);

	/**
	 * 
	 * <p>
	 * 退出群组
	 * </p>
	 *
	 * 正常退出<br/>
	 * 强制退出<br/>
	 * 
	 * 
	 * @param channel_id
	 * @return
	 */
	ResultMap<Void> quit(String channel_id);

}