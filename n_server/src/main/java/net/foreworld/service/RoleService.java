package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Role;

/**
 *
 * @author Administrator
 *
 */
public interface RoleService extends IService<Role> {

	List<Role> findByRole(Role role, int page, int rows);
}
