package net.foreworld.util;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Client {

	public abstract void start() throws Exception;

	public abstract void shutdown() throws Exception;

	public void restart() throws Exception {
		shutdown();
		start();
	}

}
