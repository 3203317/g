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
	 *            群组类型（例如：8人赛，大众场，白金场，钻石场）
	 * @param group_id
	 *            群组id（如果此字段存在，则直接进入）
	 * @return
	 */
	ResultMap<String> search(String user_id, String group_type, String group_id);

}