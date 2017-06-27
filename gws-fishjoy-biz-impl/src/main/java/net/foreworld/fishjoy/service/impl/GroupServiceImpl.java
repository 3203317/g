package net.foreworld.fishjoy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.GroupService;
import net.foreworld.service.impl.BaseService;

/**
 *
 * @author huangxin
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseService implements GroupService {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Override
	public ResultMap<SameData<String>> search(String server_id, String channel_id, String group_type) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<SameData<String>> entry(String server_id, String channel_id, String group_id) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<SameData<String>> quit(String server_id, String channel_id) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(false);
		return map;
	}

	@Override
	public ResultMap<SameData<String>> visit(String server_id, String channel_id, String group_id) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<SameData<String>> quit(String server_id, String channel_id, String group_id) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(true);
		return map;
	}

}
