package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.foreworld.gws.util.RedisUtil;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.UserService;
import net.foreworld.service.impl.BaseService;

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
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Value("${db.redis}")
	private String db_redis;

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Value("${sha.channel.close}")
	private String sha_channel_close;

	@Override
	public ResultMap<SameData<String>> logout(String server_id,
			String channel_id) {

		ResultMap<SameData<String>> map = new ResultMap<SameData<String>>();
		map.setSuccess(false);

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return map;

		List<String> s = new ArrayList<String>();
		s.add(db_redis);

		List<String> b = new ArrayList<String>();
		b.add(server_id);
		b.add(channel_id);

		Object o = j.evalsha(sha_channel_close, s, b);
		j.close();

		String str = o.toString();

		switch (str) {
		case "invalid_user_id":
		case "invalid_group_id":
			map.setCode(str);
			return map;
		}

		map.setSuccess(true);
		return map;
	}

}
