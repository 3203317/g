package net.foreworld.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.Group;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;
import net.foreworld.service.UserService;

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
	public ResultMap<String> search(String user_id, String group_type, String group_id) {

		ResultMap<String> map = new ResultMap<String>();

		Group group = new Group();
		group.setGroup_name(new Date().toString());

		save(group);

		map.setData(group.getGroup_name());

		map.setSuccess(true);
		return map;
	}

}
