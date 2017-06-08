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
		APPLY(1), AGREE(2), REJECT(3);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * 获取我的好友列表
	 * 
	 * @param my_id
	 * @param status
	 * @return
	 */
	List<UserFriends> getMyFriends(String my_id, Status status);

	/**
	 * 申请加为好友
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<Void> apply(UserFriends entity);

	/**
	 * 
	 * @param id
	 * @param friend_b
	 *            被申请人id
	 * @return
	 */
	ResultMap<Void> agree(String id, String friend_b);

	/**
	 * 被申请人拒绝加为好友
	 * 
	 * @param id
	 * @param friend_b
	 *            被申请人id
	 * @param reason
	 *            原因
	 * @return
	 */
	ResultMap<Void> reject(String id, String friend_b, String reason);

}