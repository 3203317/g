package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;
import net.foreworld.util.Md5;
import net.foreworld.util.StringUtil;
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
	public ResultMap<User> register(User entity) {

		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		String user_pass = StringUtil.isEmpty(entity.getUser_pass());

		if (null == user_pass) {
			map.setMsg("密码不能为空");
			return map;
		}

		User user = getByName(entity.getUser_name());

		if (null != user) {
			map.setMsg("重复的用户名");
			return map;
		}

		entity.setUser_pass(Md5.encode(user_pass));
		entity.setStatus(Status.ENABLE.value());
		save(entity);

		map.setData(entity);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<User> login(String user_name, String user_pass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByName(String user_name) {

		user_name = StringUtil.isEmpty(user_name);

		Assert.notNull(user_name, "user_name is not null");

		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();

		criteria.andEqualTo("user_name", user_name);

		List<User> list = selectByExample(example);
		return 1 == list.size() ? list.get(0) : null;

	}

}
