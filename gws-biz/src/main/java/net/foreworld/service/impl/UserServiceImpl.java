package net.foreworld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.User;
import net.foreworld.service.UserService;
import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("UserService")
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Override
	public int updateNotNull(User entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<User> findByUser(User entity, int page, int rows) {
		Example example = new Example(User.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

}
