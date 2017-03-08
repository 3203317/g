package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;
import net.foreworld.util.StringUtil;
import net.foreworld.util.encryptUtil.MD5;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

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
		entity.setPid(null);
		return super.updateNotNull(entity);
	}

	@Override
	public int save(User entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		entity.setStatus(Status.START.value());
		return super.save(entity);
	}

	@Override
	public ResultMap<Void> setStatus(String id, Status status) {
		ResultMap<Void> map = new ResultMap<Void>();

		User entity = new User();
		entity.setId(id);
		entity.setStatus(status.value());
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> remove(String id) {
		ResultMap<Void> map = new ResultMap<Void>();
		delete(id);
		map.setSuccess(true);
		return map;
	}

	/**
	 *
	 * @param user_name
	 * @return
	 */
	private User loginCheck(String user_name) {
		User entity = null;

		entity = new User();
		entity.setUser_name(user_name);
		entity = getByUser(entity);
		if (null != entity) {
			return entity;
		}

		entity = new User();
		entity.setEmail(user_name);
		entity = getByUser(entity);
		if (null != entity) {
			return entity;
		}

		entity = new User();
		entity.setMobile(user_name);
		entity = getByUser(entity);
		if (null != entity) {
			return entity;
		}

		return null;
	}

	@Override
	public ResultMap<User> login(String user_name, String user_pass) {
		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		User entity = loginCheck(user_name);

		if (null == entity) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		if (Status.START.value() != entity.getStatus()) {
			map.setMsg("你已被限制登陆");
			return map;
		}

		if (!MD5.encode(user_pass).equals(entity.getUser_pass())) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		map.setData(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<User> register(User entity) {
		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		entity.setPid(StringUtil.isEmpty(entity.getPid()));

		if (null != entity.getPid()) {
			User pUser = getById(entity.getPid());

			if (null == pUser)
				entity.setPid(null);
		}

		entity.setUser_pass(MD5.encode(entity.getUser_pass()));
		save(entity);

		map.setData(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> editInfo(User entity) {
		ResultMap<Void> map = new ResultMap<Void>();

		entity.setStatus(null);
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> resetPwd(String id, String user_pass) {
		ResultMap<Void> map = new ResultMap<Void>();

		User entity = new User();
		entity.setId(id);
		entity.setUser_pass(MD5.encode(user_pass));
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
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

		String pid = StringUtil.isEmpty(entity.getPid());
		if (null != pid) {
			criteria.andEqualTo("pid", pid);
		}

		String user_name = StringUtil.isEmpty(entity.getUser_name());
		if (null != user_name) {
			criteria.andEqualTo("user_name", user_name);
		}

		String real_name = StringUtil.isEmpty(entity.getReal_name());
		if (null != real_name) {
			criteria.andEqualTo("real_name", real_name);
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
	public ResultMap<Void> changePwd(String id, String old_pass, String new_pass) {
		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		new_pass = StringUtil.isEmpty(new_pass);
		if (null == new_pass) {
			map.setMsg("新密码不能为空");
			return map;
		}

		User entity = getById(id);

		if (!MD5.encode(old_pass).equals(entity.getUser_pass())) {
			map.setMsg("原密码错误");
			return map;
		}

		entity = new User();
		entity.setId(id);
		entity.setUser_pass(MD5.encode(new_pass));
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
	}
}
