package net.foreworld.gws.test;

import net.foreworld.gws.TimeClient;

/**
 *
 * @author huangxin
 *
 */
public class TestClient {

	public static void main(String[] args) {

		int port = 9988;
		if (null != args && 0 < args.length) {
			port = Integer.valueOf(args[0]);
		}

		new TimeClient(port, "127.0.0.1");
	}

}
