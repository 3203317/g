package net.foreworld.service;

import java.util.List;

import net.foreworld.model.UserFriends;

/**
 * 
 * @author huangxin
 *
 */
public interface UserFriendsService extends IService<UserFriends> {

	/**
	 * 
	 * @param my_id
	 *            我的id
	 * @return
	 */
	List<UserFriends> findByMyFriends(String my_id);

}