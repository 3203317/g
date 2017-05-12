package net.foreworld.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:redis.properties")
@Component
public final class RedisUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisUtil.class);

	@Value("${db.maxIdle}")
	private int maxIdle;

	@Value("${db.maxWaitMillis}")
	private int maxWaitMillis;

	@Value("${db.testOnBorrow:true}")
	private boolean testOnBorrow;

	@Value("${db.host}")
	private String host;

	@Value("${db.port}")
	private int port;

	@Value("${db.timeout}")
	private int timeout;

	@Value("${db.pass}")
	private String pass;

	private JedisPool jedisPool = null;

	private void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWaitMillis);
			config.setTestOnBorrow(testOnBorrow);
			// timeout 最大延迟时间
			jedisPool = new JedisPool(config, host, port, timeout, pass);
		} catch (Exception e) {
			logger.error("initPool", e);
		}
	}

	public Jedis getJedis() {
		if (null == jedisPool) {
			initPool();
		}
		try {
			return null == jedisPool ? null : jedisPool.getResource();
		} catch (Exception e) {
			logger.error("getJedis", e);
			return null;
		}
	}
}
