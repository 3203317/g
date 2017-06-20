package net.foreworld.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;

/**
 *
 * @author huangxin
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseService implements GroupService {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Override
	public ResultMap<Void> search(String server_id, String channel_id, String group_type) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		quit(server_id, channel_id);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> entry(String server_id, String channel_id, String group_id) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		quit(server_id, channel_id);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> quit(String server_id, String channel_id) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);
		return map;
	}

}
