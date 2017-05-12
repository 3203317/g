package net.foreworld.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:redis.properties")
public final class RedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	@Value("${db.maxIdle}")
	private static int maxIdle;

	@Value("${db.maxWaitMilli}")
	private static int maxWaitMillis;

	@Value("${db.testOnBorrow:true}")
	private static boolean testOnBorrow;

	@Value("${db.host}")
	private static String host;

	@Value("${db.port}")
	private static int port;

	@Value("${db.timeout}")
	private static int timeout;

	@Value("${db.pass}")
	private static String pass;

	private static JedisPool jedisPool = null;

	private RedisUtil() {
	}

	private static synchronized void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWaitMillis);
			config.setTestOnBorrow(testOnBorrow);
			// TIMEOUT 最大延迟时间
			jedisPool = new JedisPool(config, host, port, timeout, pass);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取jedis实例
	 *
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		if (null == jedisPool) {
			initPool();
		}
		try {
			return null == jedisPool ? null : jedisPool.getResource();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
}
