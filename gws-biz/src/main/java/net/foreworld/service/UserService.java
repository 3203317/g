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
	 * 用户注册
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
	 * 通过用户名获取用户
	 * 
	 * @param user_name
	 * @return
	 */
	User getByName(String user_name);

	/**
	 * 修改用户的密码
	 * 
	 * @param id
	 * @param old_pass
	 *            老密码
	 * @param new_pass
	 *            新密码
	 * @return
	 */
	ResultMap<Void> changePwd(String id, String old_pass, String new_pass);

}