package net.foreworld.service;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;

/**
 *
 * @author Administrator
 *
 */
public interface ManagerService extends IService<Manager> {

	Manager getByManager(Manager manager);


	ResultMap<Manager> login(String user_name, String user_pass);

	ResultMap<Void> changePwd(String user_id, String old_pass, String new_pass);
}
