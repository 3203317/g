package net.foreworld.gws;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Server {

	public abstract void start();

	public abstract void shutdown();

	public void restart() {
		shutdown();
		start();
	}

}
