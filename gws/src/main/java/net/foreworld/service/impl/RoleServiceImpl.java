package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.Role;
import net.foreworld.service.RoleService;
import net.foreworld.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("roleService")
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

	@Override
	public int updateNotNull(Role entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public int save(Role entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		entity.setStatus(Status.START.value());
		return super.save(entity);
	}

	@Override
	public List<Role> findByRole(Role entity, int page, int rows) {
		Example example = new Example(Role.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<Void> editInfo(Role entity) {
		ResultMap<Void> map = new ResultMap<Void>();
		entity.setStatus(null);
		updateNotNull(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Role> saveNew(Role entity) {
		ResultMap<Role> map = new ResultMap<Role>();
		map.setSuccess(false);

		Role byDb = getByRole_name(entity.getRole_name());
		if (null != byDb) {
			map.setMsg("role is exist");
			return map;
		}

		save(entity);
		map.setData(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> setStatus(String id, Status status) {
		ResultMap<Void> map = new ResultMap<Void>();

		Role entity = new Role();
		entity.setId(id);
		entity.setStatus(status.value());
		updateNotNull(entity);

		map.setSuccess(true);
		return map;
	}

	/**
	 *
	 * @param role_name
	 *            角色名
	 * @return role
	 */
	private Role getByRole_name(String role_name) {
		role_name = StringUtil.isEmpty(role_name);

		if (null == role_name) {
			return null;
		}

		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("role_name", role_name);
		List<Role> list = selectByExample(example);
		Assert.notNull(list, "role list is null");
		return 1 == list.size() ? list.get(0) : null;
	}
}
