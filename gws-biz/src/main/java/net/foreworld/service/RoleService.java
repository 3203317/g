package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Role;

/**
 * 
 * @author huangxin
 *
 */
public interface RoleService extends IService<Role> {

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

	List<Role> findByRole(Role entity, int page, int rows);

}