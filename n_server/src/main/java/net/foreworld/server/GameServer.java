package net.foreworld.server;

/**
 * 游戏服务器
 *
 * @author Administrator
 *
 */
public class GameServer {

	/**
	 * 服务器状态
	 *
	 * @author Administrator
	 *
	 */
	enum State {
		INITED, START, STARTED, STOPED
	}

	private int port;
	private String version;

	private State state;

	public String getVersion() {
		return version;
	}

	public GameServer(int port) {
		this.version = "1.0.6";
		this.port = port;

		this.state = State.INITED;
	}

	public void start() {
		System.out.println(this.port);
	}

	public void stop() {

	}

}
