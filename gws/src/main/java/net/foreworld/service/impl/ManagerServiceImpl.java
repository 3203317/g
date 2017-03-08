package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ManagerService;
import net.foreworld.util.StringUtil;
import net.foreworld.util.encryptUtil.MD5;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("manageService")
public class ManagerServiceImpl extends BaseService<Manager> implements
		ManagerService {

	@Override
	public int save(Manager entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		entity.setStatus(Status.START.value());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(Manager entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	/**
	 *
	 * @param user_name
	 * @return
	 */
	private Manager loginCheck(String user_name) {
		Manager entity = null;

		entity = new Manager();
		entity.setUser_name(user_name);
		entity = getByManager(entity);
		if (null != entity) {
			return entity;
		}

		entity = new Manager();
		entity.setEmail(user_name);
		entity = getByManager(entity);
		if (null != entity) {
			return entity;
		}

		return null;
	}

	@Override
	public ResultMap<Manager> login(String user_name, String user_pass) {
		ResultMap<Manager> map = new ResultMap<Manager>();
		map.setSuccess(false);

		Manager entity = loginCheck(user_name);

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
	public ResultMap<Void> changePwd(String id, String old_pass, String new_pass) {
		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		new_pass = StringUtil.isEmpty(new_pass);
		if (null == new_pass) {
			map.setMsg("新密码不能为空");
			return map;
		}

		Manager entity = getById(id);

		if (!MD5.encode(old_pass).equals(entity.getUser_pass())) {
			map.setMsg("原密码错误");
			return map;
		}

		entity = new Manager();
		entity.setId(id);
		entity.setUser_pass(MD5.encode(new_pass));
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
	}

	@Override
	public Manager getByManager(Manager entity) {
		if (null == entity)
			return null;

		Example example = new Example(Manager.class);
		Example.Criteria criteria = example.createCriteria();

		String email = StringUtil.isEmpty(entity.getEmail());
		if (null != email) {
			criteria.andEqualTo("email", email);
		}

		String user_name = StringUtil.isEmpty(entity.getUser_name());
		if (null != user_name) {
			criteria.andEqualTo("user_name", user_name);
		}

		String nickname = StringUtil.isEmpty(entity.getNickname());
		if (null != nickname) {
			criteria.andEqualTo("nickname", nickname);
		}

		List<Manager> list = selectByExample(example);
		Assert.notNull(list, "manager list is null");
		return 1 == list.size() ? list.get(0) : null;
	}

}
