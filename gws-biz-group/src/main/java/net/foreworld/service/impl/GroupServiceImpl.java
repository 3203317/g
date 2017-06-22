package net.foreworld.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
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
	public ResultMap<List<Receiver<User>>> search(String server_id, String channel_id, String group_type) {

		ResultMap<List<Receiver<User>>> map = new ResultMap<List<Receiver<User>>>();
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<User>>> entry(String server_id, String channel_id, String group_id) {

		ResultMap<List<Receiver<User>>> map = new ResultMap<List<Receiver<User>>>();
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<User>>> quit(String server_id, String channel_id) {

		ResultMap<List<Receiver<User>>> map = new ResultMap<List<Receiver<User>>>();
		map.setSuccess(false);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<User>>> view(String server_id, String channel_id, String group_id) {

		ResultMap<List<Receiver<User>>> map = new ResultMap<List<Receiver<User>>>();
		map.setSuccess(true);
		return map;
	}

}
