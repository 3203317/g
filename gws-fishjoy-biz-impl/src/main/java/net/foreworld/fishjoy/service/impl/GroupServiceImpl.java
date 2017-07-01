package net.foreworld.fishjoy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;
import net.foreworld.service.impl.BaseService;
import net.foreworld.util.StringUtil;

/**
 *
 * @author huangxin
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseService implements GroupService {

	@Value("${invalid.group.type}")
	private String invalid_group_type;

	@Value("${invalid.group.id}")
	private String invalid_group_id;

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Override
	public ResultMap<List<Receiver<String>>> search(String server_id, String channel_id, String group_type) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		group_type = StringUtil.isEmpty(group_type);

		if (null == group_type) {
			map.setCode(invalid_group_type);
			return map;
		}

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<String>>> entry(String server_id, String channel_id, String group_id) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		group_id = StringUtil.isEmpty(group_id);

		if (null == group_id) {
			map.setCode(invalid_group_id);
			return map;
		}

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<String>>> quit(final String server_id, final String channel_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<List<Receiver<String>>> visit(String server_id, String channel_id, String group_id) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		group_id = StringUtil.isEmpty(group_id);

		if (null == group_id) {
			map.setCode(invalid_group_id);
			return map;
		}

		map.setSuccess(true);
		return map;
	}

}
