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

	List<User> findByUser(User entity, int page, int rows);

	/**
	 * 注册新用户
	 * 
	 * @param entity
	 * @return
	 */
	ResultMap<User> register(User entity);

}