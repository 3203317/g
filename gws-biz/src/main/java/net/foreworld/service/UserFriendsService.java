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

	/**
	 * 获取我的好友
	 * 
	 * @param my_id
	 * @return
	 */
	List<UserFriends> findMyFriends(String my_id);

	/**
	 * 申请加为好友
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<Void> apply(UserFriends entity);

	/**
	 * 判断双方是否是好友
	 * 
	 * @param my_id
	 *            我的id
	 * @param friend_id
	 *            朋友的id
	 * @return
	 */
	ResultMap<Void> checkFriends(String my_id, String friend_id);

}