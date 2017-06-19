package net.foreworld.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.Group;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;

/**
 *
 * @author huangxin
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Override
	public ResultMap<String> search(String user_id, String group_type) {

		ResultMap<String> map = new ResultMap<String>();

		Group group = new Group();
		group.setGroup_name(new Date().toString());

		map.setData(group.getGroup_name());

		map.setData("成功了吗？");
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<String> entry(String user_id, String group_id) {

		ResultMap<String> map = new ResultMap<String>();
		map.setSuccess(false);

		quit(user_id);

		Group group = new Group();
		group.setGroup_name(new Date().toString());

		map.setData(group.getGroup_name());

		map.setData("群组号");
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> quit(String channel_id) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);
		return map;
	}

}
