package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;
import net.foreworld.util.RestUtil;
import net.foreworld.util.StringUtil;
import net.foreworld.util.encryptUtil.MD5;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Override
	public int save(User entity) {
		entity.setCreate_time(new Date());
		entity.setStatus(1);
		return super.save(entity);
	}

	@Override
	public int updateNotNull(User entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	/**
	 * 根据（用户名、邮箱、手机号）获取用户
	 *
	 * 用户名、邮箱、手机号（任意一个都可以登陆）
	 *
	 * @param user_name
	 * @return user 用户
	 */
	private User login_user(String user_name) {
		User _user = null;

		_user = new User();
		_user.setUser_name(user_name);
		_user = getByUser(_user);
		if (null != _user) {
			return _user;
		}

		_user = new User();
		_user.setEmail(user_name);
		_user = getByUser(_user);
		if (null != _user) {
			return _user;
		}

		_user = new User();
		_user.setMobile(user_name);
		_user = getByUser(_user);
		if (null != _user) {
			return _user;
		}

		return null;
	}

	@Override
	public ResultMap<User> login(String user_name, String user_pass) {

		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		User user = login_user(user_name);

		if (null == user) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		if (!MD5.encode(user_pass).equals(user.getUser_pass())) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		if (0 == user.getStatus() || 2 == user.getStatus()) {
			map.setMsg("已被限制登陆，请联系管理员");
			return map;
		}

		map.setData(user);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<User> register(User user) {

		ResultMap<User> map = register_params_check(user);

		if (!map.getSuccess()) {
			return map;
		}

		user = map.getData();

		// 检测推荐人
		if (null != user.getPid()) {

			User tjr = selectByKey(user.getPid());

			// 推荐人不存在，则重置为null
			if (null == tjr) {
				user.setPid(null);
			}
		}

		user.setUser_pass(MD5.encode(user.getUser_pass()));
		save(user);

		return map;
	}

	/**
	 * 对象字段验证
	 *
	 * @param user
	 * @return
	 */
	private ResultMap<User> register_params_check(User user) {

		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		user.setUser_name(StringUtil.isEmpty(user.getUser_name()));
		if (null == user.getUser_name()) {
			map.setMsg("用户名不能为空");
			return map;
		}

		user.setUser_pass(StringUtil.isEmpty(user.getUser_pass()));
		if (null == user.getUser_pass()) {
			map.setMsg("登陆密码不能为空");
			return map;
		}

		User _user = null;

		_user = new User();
		_user.setUser_name(user.getUser_name());
		_user = getByUser(_user);
		if (null != _user) {
			map.setMsg("用户名已经存在");
			return map;
		}

		map.setData(user);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> editInfo(User user) {

		ResultMap<Void> map = new ResultMap<Void>();

		User _user = new User();
		_user.setId(user.getId());
		_user.setReal_name(user.getReal_name());
		_user.setAlipay_account(user.getAlipay_account());
		_user.setWx_account(user.getWx_account());
		updateNotNull(_user);

		map.setSuccess(true);
		return map;
	}

	@Override
	public User getByUser(User user) {

		if (null == user) {
			return null;
		}

		Example example = new Example(User.class);

		Example.Criteria criteria = example.createCriteria();

		String email = StringUtil.isEmpty(user.getEmail());
		if (null != email) {
			criteria.andEqualTo("email", email);
		}

		String mobile = StringUtil.isEmpty(user.getMobile());
		if (null != mobile) {
			criteria.andEqualTo("mobile", mobile);
		}

		String nickname = StringUtil.isEmpty(user.getNickname());
		if (null != nickname) {
			criteria.andEqualTo("nickname", nickname);
		}

		String user_name = StringUtil.isEmpty(user.getUser_name());
		if (null != user_name) {
			criteria.andEqualTo("user_name", user_name);
		}

		String apikey = StringUtil.isEmpty(user.getApikey());
		if (null != apikey) {
			criteria.andEqualTo("apikey", apikey);
		}

		String seckey = StringUtil.isEmpty(user.getSeckey());
		if (null != seckey) {
			criteria.andEqualTo("seckey", seckey);
		}

		List<User> list = selectByExample(example);
		return (null == list || 1 != list.size()) ? null : list.get(0);
	}

	@Override
	public List<User> findByUser(User user, int page, int rows) {

		Example example = new Example(User.class);
		example.setOrderByClause("create_time Desc");

		if (null != user) {
			Example.Criteria criteria = example.createCriteria();

			String nickname = StringUtil.isEmpty(user.getNickname());
			if (null != nickname) {
				criteria.andLike("nickname", "%" + nickname + "%");
			}
		}

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<Void> changePwd(String user_id, String old_pass,
			String new_pass) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		new_pass = StringUtil.isEmpty(new_pass);
		if (null == new_pass) {
			map.setMsg("新密码不能为空");
			return map;
		}

		User user = selectByKey(user_id);

		if (!MD5.encode(old_pass).equals(user.getUser_pass())) {
			map.setMsg("原密码错误");
			return map;
		}

		user = new User();
		user.setId(user_id);
		user.setUser_pass(MD5.encode(new_pass));
		updateNotNull(user);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> resetPwd(String user_id, String user_pass) {

		ResultMap<Void> map = new ResultMap<Void>();

		User user = new User();
		user.setId(user_id);
		user.setUser_pass(MD5.encode(user_pass));
		updateNotNull(user);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> remove(String user_id) {

		ResultMap<Void> map = new ResultMap<Void>();

		User user = new User();
		user.setId(user_id);
		user.setStatus(2);
		updateNotNull(user);

		map.setSuccess(true);
		return map;
	}

	private String genUserApiKey() {
		String encodedKey = null;
		User user = null;
		do {
			encodedKey = RestUtil.genApiKey();
			user = findByApiKey(encodedKey);
		} while (user != null);
		return encodedKey;
	}

	private String genUserSecKey() {
		String encodedKey = null;
		User user = null;
		do {
			encodedKey = RestUtil.genApiKey();
			user = findBySecKey(encodedKey);
		} while (user != null);
		return encodedKey;
	}

	private User findBySecKey(String seckey) {
		seckey = StringUtil.isEmpty(seckey);
		if (null == seckey) {
			return null;
		}

		User user = new User();
		user.setSeckey(seckey);

		return getByUser(user);
	}

	private User findByApiKey(String apikey) {
		apikey = StringUtil.isEmpty(apikey);
		if (null == apikey) {
			return null;
		}

		User user = new User();
		user.setApikey(apikey);

		return getByUser(user);
	}
}
