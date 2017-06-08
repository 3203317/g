package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.Group;
import net.foreworld.service.GroupService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author huangxin
 *
 */
@Service("roleService")
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

	@Override
	public int updateNotNull(Group entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public int save(Group entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public List<Group> findByRole(Group entity, int page, int rows) {
		Example example = new Example(Group.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

}
