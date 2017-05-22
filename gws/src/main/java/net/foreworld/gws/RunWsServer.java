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
import org.springframework.jms.core.JmsMessagingTemplate;

import net.foreworld.gws.server.WsServer;

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

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Value("${server.id}")
	private String server_id;

	public static void main(String[] args) {
		SpringApplication.run(RunWsServer.class, args);
	}

	public void run(String... strings) throws Exception {

		clearRedis();

		try {
			wsServer.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void clearRedis() {
		jmsMessagingTemplate.convertAndSend("server.start", server_id);
	}

}
