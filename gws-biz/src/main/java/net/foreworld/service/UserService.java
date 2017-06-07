package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;

/**
 * 
 * @author huangxin
 *
 */
public interface UserService extends IService<User> {

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

}