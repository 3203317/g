package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.UserFriends;

/**
 * 
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserFriendsService extends IService<UserFriends> {

	enum Status {
		ENABLE(1), DISABLE(0);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	List<UserFriends> findByUserFriends(UserFriends entity, int page, int rows);

	/**
	 * 申请加为好友
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<UserFriends> apply(UserFriends entity);

}