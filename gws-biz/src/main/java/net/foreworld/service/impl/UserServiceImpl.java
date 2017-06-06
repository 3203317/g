package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

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
@Service("UserService")
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

		entity.setP_id(StringUtil.isEmpty(entity.getP_id()));

		if (null != entity.getP_id()) {
			User p_user = getById(entity.getP_id());

			if (null == p_user)
				entity.setP_id(null);
		}

		entity.setUser_pass(Md5.encode(entity.getUser_pass()));
		save(entity);

		map.setData(entity);
		map.setSuccess(true);
		return map;

	}

}
