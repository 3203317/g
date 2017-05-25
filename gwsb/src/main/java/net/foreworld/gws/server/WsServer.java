package net.foreworld.gws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:server.properties")
@Component
public class WsServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(WsServer.class);

	@Override
	public void start() {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				beforeShut();
				shutdown();
			}
		});
	}

	@Override
	public void shutdown() {
	}

	private void beforeShut() {
	}

}
