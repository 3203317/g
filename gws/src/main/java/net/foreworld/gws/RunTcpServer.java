package net.foreworld.gws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import net.foreworld.gws.server.TcpServer;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@SpringBootApplication
@ComponentScan("net.foreworld.gws")
public class RunTcpServer implements CommandLineRunner {

	@Autowired
	private TcpServer tcpServer;

	public static void main(String[] args) {
		SpringApplication.run(RunTcpServer.class, args);
	}

	public void run(String... strings) throws Exception {
		try {
			tcpServer.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
