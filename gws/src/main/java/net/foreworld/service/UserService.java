package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserService extends IService<User> {

	enum Status {
		START(1), STOP(0);

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
	 * @param id
	 * @param status
	 * @return
	 */
	ResultMap<Void> setStatus(String id, Status status);

	/**
	 *
	 * @param id
	 * @return
	 */
	ResultMap<Void> remove(String id);

	/**
	 *
	 * @param user_name
	 * @param user_pass
	 * @return
	 */
	ResultMap<User> login(String user_name, String user_pass);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<User> register(User entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<Void> editInfo(User entity);

	/**
	 *
	 * @param id
	 * @param user_pass
	 * @return
	 */
	ResultMap<Void> resetPwd(String id, String user_pass);

	/**
	 *
	 * @param entity
	 * @param page
	 * @param rows
	 * @return
	 */
	List<User> findByUser(User entity, int page, int rows);

	/**
	 *
	 * @param entity
	 * @return
	 */
	User getByUser(User entity);

	/**
	 *
	 * @param id
	 * @param old_pass
	 * @param new_pass
	 * @return
	 */
	ResultMap<Void> changePwd(String id, String old_pass, String new_pass);

}