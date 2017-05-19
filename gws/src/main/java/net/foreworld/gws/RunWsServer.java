package net.foreworld.gws;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import net.foreworld.gws.server.WsServer;
import net.foreworld.util.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:redis.properties")
@PropertySource("classpath:server.properties")
@SpringBootApplication
@ComponentScan("net.foreworld")
public class RunWsServer implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RunWsServer.class);

	@Resource(name = "wsServer")
	private WsServer wsServer;

	@Value("${server.id}")
	private String server_id;

	@Value("${sha.server.reset}")
	private String sha_server_reset;

	public static void main(String[] args) {
		SpringApplication.run(RunWsServer.class, args);
	}

	public void run(String... strings) throws Exception {

		if (!resetRedis()) {
			return;
		}

		try {
			wsServer.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 
	 * 1. 重置服务器的连接数为 0 <br/>
	 * 2. 设置服务器的状态为 start
	 * 
	 * @return
	 */
	private boolean resetRedis() {

		List<String> s = new ArrayList<String>();
		s.add("server_id");
		s.add("status");

		List<String> b = new ArrayList<String>();
		b.add(server_id);
		b.add("START");

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return false;

		Object o = j.evalsha(sha_server_reset, s, b);
		j.close();

		if (null == o || !"OK".equals(o)) {
			return false;
		}

		return true;
	}

}
