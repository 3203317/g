package net.foreworld.gws;

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
@PropertySource("classpath:server.properties")
@SpringBootApplication
@ComponentScan("net.foreworld")
public class RunWsServer implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(RunWsServer.class);

	@Resource(name = "wsServer")
	private WsServer wsServer;

	@Value("${server.id}")
	private String server_id;

	public static void main(String[] args) {
		SpringApplication.run(RunWsServer.class, args);
	}

	public void run(String... strings) throws Exception {

		if (!beforeStart()) {
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
	 * 1. 重置服务器的连接数为 0 <br/>
	 * 2. 设置服务器的状态为 start
	 * 
	 * @return
	 */
	private boolean beforeStart() {

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return false;

		j.close();

		return true;
	}

}
