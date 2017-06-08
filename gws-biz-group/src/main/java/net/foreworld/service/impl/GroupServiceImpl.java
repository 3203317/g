package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.Group;
import net.foreworld.model.User;
import net.foreworld.service.GroupService;
import net.foreworld.service.UserService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author huangxin
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

	@Resource
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

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
	public List<Group> findByGroup(Group entity, int page, int rows) {

		List<User> list = userService.selectByExample(null);
		logger.info("{}", list.size());

		Example example = new Example(Group.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

}
