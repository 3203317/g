package net.foreworld.util;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Client {

	public abstract void start();

	public abstract void shutdown();

	public void restart() {
		shutdown();
		start();
	}

}
