package net.foreworld.gws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.foreworld.gws.server.WsServer;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@SpringBootApplication
@ComponentScan("net.foreworld.gws")
public class RunWsServer implements CommandLineRunner {

	@Autowired
	private WsServer wsServer;

	public static void main(String[] args) {
		SpringApplication.run(RunWsServer.class, args);
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
