package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Group;

/**
 * 
 * @author huangxin
 *
 */
public interface GroupService extends IService<Group> {

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

	List<Group> findByRole(Group entity, int page, int rows);

}