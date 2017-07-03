package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.foreworld.gws.util.RedisUtil;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;
import net.foreworld.service.impl.BaseService;
import net.foreworld.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

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

	private static final Logger logger = LoggerFactory
			.getLogger(GroupServiceImpl.class);

	@Override
	public ResultMap<List<Receiver<String>>> search(String server_id,
			String channel_id, String group_type) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		group_type = StringUtil.isEmpty(group_type);

		if (null == group_type) {
			map.setCode(invalid_group_type);
			return map;
		}

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return map;

		List<String> s = new ArrayList<String>();

		List<String> b = new ArrayList<String>();
		b.add("a");
		b.add("b");
		b.add("fishjoy");
		b.add(group_type);

		Object o = j.evalsha("b37fdb5ba37121743a351c1e65e701ab709e32d8", s, b);
		j.close();

		System.out.println(o);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<String>>> entry(String server_id,
			String channel_id, String group_id) {

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
	public ResultMap<List<Receiver<String>>> quit(final String server_id,
			final String channel_id) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<String>>> visit(String server_id,
			String channel_id, String group_id) {

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
