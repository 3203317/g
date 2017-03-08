package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.Role;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface RoleService extends IService<Role> {

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

	List<Role> findByRole(Role entity, int page, int rows);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<Void> editInfo(Role entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<Role> saveNew(Role entity);

	/**
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	ResultMap<Void> setStatus(String id, Status status);

}