package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;
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
	public int updateNotNull(User entity) {
		entity.setCreate_time(null);
		entity.setP_id(null);
		return super.updateNotNull(entity);
	}

	@Override
	public int save(User entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public List<User> findByUser(User entity, int page, int rows) {
		Example example = new Example(User.class);
		example.setOrderByClause("create_time DESC");

		if (null != entity) {
			Example.Criteria criteria = example.createCriteria();

			String nickname = StringUtil.isEmpty(entity.getNickname());
			if (null != nickname) {
				criteria.andLike("nickname", "%" + nickname + "%");
			}

			String user_name = StringUtil.isEmpty(entity.getUser_name());
			if (null != user_name) {
				criteria.andLike("user_name", "%" + user_name + "%");
			}
		}

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public User getByUser(User entity) {
		if (null == entity)
			return null;

		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();

		String pid = StringUtil.isEmpty(entity.getP_id());
		if (null != pid) {
			criteria.andEqualTo("pid", pid);
		}

		String user_name = StringUtil.isEmpty(entity.getUser_name());
		if (null != user_name) {
			criteria.andEqualTo("user_name", user_name);
		}

		String mobile = StringUtil.isEmpty(entity.getMobile());
		if (null != mobile) {
			criteria.andEqualTo("mobile", mobile);
		}

		String nickname = StringUtil.isEmpty(entity.getNickname());
		if (null != nickname) {
			criteria.andEqualTo("nickname", nickname);
		}

		String email = StringUtil.isEmpty(entity.getEmail());
		if (null != email) {
			criteria.andEqualTo("email", email);
		}

		String qq = StringUtil.isEmpty(entity.getQq());
		if (null != qq) {
			criteria.andEqualTo("qq", qq);
		}

		List<User> list = selectByExample(example);
		Assert.notNull(list, "user list is null");
		return 1 == list.size() ? list.get(0) : null;
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
