package net.foreworld.server;

/**
 * 服务器启动
 *
 * @author Administrator
 *
 */
public class GameStart implements Runnable {

	private GameServer server;

	public static void main(String[] args) {
		GameStart start = new GameStart();
		start.run();
	}

	@Override
	public void run() {
		server = new GameServer(6666);
		server.start();
	}
}
