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

	@Value("${sha.group.search}")
	private String sha_group_search;

	@Value("${app.name}")
	private String app_name;

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
		b.add(server_id);
		b.add(channel_id);
		b.add(app_name);
		b.add(group_type);

		Object o = j.evalsha(sha_group_search, s, b);
		j.close();

		System.out.println(o);

		String str = o.toString();

		switch (str) {
		case "invalid_channel":
		case "invalid_database":
		case "non_idle_pos":
			map.setCode(str);
			return map;
		}

		//

		List<Receiver<String>> list = new ArrayList<Receiver<String>>();

		String[] q = str.substring(1, str.length() - 1).split(",");

		for (int m = 0, n = q.length; m < n; m++) {
			String k = q[m].toString().trim();
			String l = q[++m].toString().trim();

			String[] u = l.split("::");

			Receiver<String> rec = new Receiver<String>();
			rec.setServer_id(u[0]);
			rec.setChannel_id(u[1]);
			rec.setData(str);
			list.add(rec);
		}

		map.setData(list);
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

	public static void main(String[] args) {

		String str = "[2, a::b::h, a, b, c, d]";

		String a = str.substring(1, str.length() - 1);

		String[] b = a.split(",");

		for (int i = 0, j = b.length; i < j; i++) {
			String c = b[i].toString().trim();
			String d = b[++i].toString().trim();
			System.out.println(c + " " + d);
		}

	}

}
