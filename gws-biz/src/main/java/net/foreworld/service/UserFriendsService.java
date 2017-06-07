package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.UserFriends;

/**
 * 
 * @author huangxin
 *
 */
public interface UserFriendsService extends IService<UserFriends> {

	enum Status {
		APPLY(1);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * 
	 * 获取我的好友列表
	 * 
	 * @param my_id
	 *            我的id
	 * @return
	 */
	List<UserFriends> getMyFriends(String my_id);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<Void> apply(UserFriends entity);

}