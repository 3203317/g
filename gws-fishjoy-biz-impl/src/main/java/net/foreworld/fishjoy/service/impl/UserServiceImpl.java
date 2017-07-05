package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.foreworld.gws.util.Constants;
import net.foreworld.gws.util.RedisUtil;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
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

	@Value("${db.redis}")
	private String db_redis;

	@Value("${sha.channel.close}")
	private String sha_channel_close;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public ResultMap<SameData<Void>> logout(String server_id, String channel_id) {

		ResultMap<SameData<Void>> map = new ResultMap<SameData<Void>>();
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

		map.setSuccess(Constants.OK.equals(o));
		return map;
	}

}
