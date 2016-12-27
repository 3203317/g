package net.foreworld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.ManagerService;
import net.foreworld.util.StringUtil;
import net.foreworld.util.encryptUtil.MD5;

/**
 *
 * @author Administrator
 *
 */
@Service("manageService")
public class ManagerServiceImpl extends BaseService<Manager> implements
		ManagerService {

	/**
	 * 登陆检测：用户名、邮箱
	 *
	 * @param user_name
	 * @return manager
	 */
	private Manager loginCheck(String user_name) {
		Manager _manager = null;

		_manager = new Manager();
		_manager.setUser_name(user_name);
		_manager = getByManager(_manager);
		if (null != _manager) {
			return _manager;
		}

		_manager = new Manager();
		_manager.setEmail(user_name);
		_manager = getByManager(_manager);
		if (null != _manager) {
			return _manager;
		}

		return null;
	}

	@Override
	public ResultMap<Manager> login(String user_name, String user_pass) {

		ResultMap<Manager> map = new ResultMap<Manager>();
		map.setSuccess(false);

		Manager manager = loginCheck(user_name);

		if (null == manager) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		if (!MD5.encode(user_pass).equals(manager.getUser_pass())) {
			map.setMsg("用户名或密码输入错误");
			return map;
		}

		if (0 == manager.getStatus()) {
			map.setMsg("你已被限制登陆");
			return map;
		}

		map.setData(manager);
		map.setSuccess(true);
		return map;
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

		Manager user = selectByKey(user_id);

		if (!MD5.encode(old_pass).equals(user.getUser_pass())) {
			map.setMsg("原密码错误");
			return map;
		}

		user = new Manager();
		user.setId(user_id);
		user.setUser_pass(MD5.encode(new_pass));
		updateNotNull(user);

		map.setSuccess(true);
		return map;
	}

	@Override
	public Manager getByManager(Manager manager) {
		Example example = new Example(User.class);

		if (null != manager) {
			Example.Criteria criteria = example.createCriteria();

			String email = StringUtil.isEmpty(manager.getEmail());
			if (null != email) {
				criteria.andEqualTo("email", email);
			}

			String user_name = StringUtil.isEmpty(manager.getUser_name());
			if (null != user_name) {
				criteria.andEqualTo("user_name", user_name);
			}
		}

		List<Manager> list = selectByExample(example);
		return (null == list || 1 != list.size()) ? null : list.get(0);
	}

}
