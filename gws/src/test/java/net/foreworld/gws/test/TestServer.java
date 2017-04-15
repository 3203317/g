package net.foreworld.gws.test;

import net.foreworld.gws.Server;
import net.foreworld.gws.WsServer;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class TestServer {

	public static void main(String[] args) {
		Server s = new WsServer(9988, 0, 0);
		s.start();
	}

}
