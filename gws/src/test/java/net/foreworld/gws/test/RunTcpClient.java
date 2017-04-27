package net.foreworld.gws.test;

import javax.annotation.Resource;

import net.foreworld.gws.client.TcpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author huangxin
 *
 */
@SpringBootApplication
@ComponentScan("net.foreworld.gws")
public class RunTcpClient implements CommandLineRunner {

	private static final Logger logger = LoggerFactory
			.getLogger(RunTcpClient.class);

	@Resource(name = "tcpClient")
	private TcpClient tcpClient;

	public static void main(String[] args) {
		SpringApplication.run(RunTcpClient.class, args);
	}

	public void run(String... strings) throws Exception {
		try {
			tcpClient.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
