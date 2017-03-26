package net.foreworld.test;

import net.foreworld.gws.Server;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class TestServer {

	public static void main(String[] args) {
		Server gs = new GameServer();
		gs.start();
	}

}
