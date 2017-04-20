package net.foreworld.gws.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.foreworld.gws.WsServer;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@SpringBootApplication
@ComponentScan("net.foreworld.gws")
public class TestServer implements CommandLineRunner {

	@Autowired
	private WsServer wsServer;

	public static void main(String[] args) {
		SpringApplication.run(TestServer.class, args);
	}

	public void run(String... strings) throws Exception {
		try {
			wsServer.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
