package net.foreworld.service;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface ManagerService extends IService<Manager> {

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

	Manager getByManager(Manager entity);

	ResultMap<Manager> login(String user_name, String user_pass);

	ResultMap<Void> changePwd(String id, String old_pass, String new_pass);
}
