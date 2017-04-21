package net.foreworld.gws.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.foreworld.gws.client.TcpClient;

/**
 *
 * @author huangxin
 *
 */
@SpringBootApplication
@ComponentScan("net.foreworld.gws")
public class TestClient implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	@Autowired
	private TcpClient tcpClient;

	public static void main(String[] args) {
		SpringApplication.run(TestClient.class, args);
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
