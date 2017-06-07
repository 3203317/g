package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;
import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Override
	public int save(User entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(User entity) {
		entity.setCreate_time(null);
		entity.setP_id(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<User> findByUser(User entity, int page, int rows) {
		Example example = new Example(User.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<User> login(String user_name, String user_pass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<User> register(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
