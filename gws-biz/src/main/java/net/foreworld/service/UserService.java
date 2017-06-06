package net.foreworld.service;

import java.util.List;

import net.foreworld.model.User;

/**
 * 
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserService extends IService<User> {

	List<User> findByUser(User entity, int page, int rows);

}