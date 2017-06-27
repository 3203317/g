package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.foreworld.gws.util.Constants;
import net.foreworld.gws.util.RedisUtil;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.UserService;
import net.foreworld.service.impl.BaseService;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${sha.user.logout}")
	private String sha_user_logout;

	@Override
	public ResultMap<List<Receiver<String>>> logout(String server_id, String channel_id) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return map;

		List<String> s = new ArrayList<String>();
		s.add("server_id");
		s.add("channel_id");

		List<String> b = new ArrayList<String>();
		b.add(server_id);
		b.add(channel_id);

		Object o = j.evalsha(sha_user_logout, s, b);
		j.close();

		if (!Constants.OK.equals(o)) {
			map.setMsg(o.toString());
			return map;
		}

		map.setSuccess(true);
		return map;
	}

}
