package net.foreworld.test;

import net.foreworld.gws.Client;
import net.foreworld.gws.WsClient;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class TestClient {

	public static void main(String[] args) {
		Client c = new WsClient("127.0.0.1", 9988);
		c.start();
	}

}
