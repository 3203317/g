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
		ENABLE(1), DISABLE(0);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	enum Sex {
		male(1), female(0);

		private int value = 0;

		private Sex(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	List<User> findByUser(User entity, int page, int rows);

	/**
	 * 注册新用户
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<User> register(User entity);

}