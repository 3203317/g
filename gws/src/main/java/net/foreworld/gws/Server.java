package net.foreworld.gws;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Server {

	public abstract void start();

	public abstract void stop();

	public void restart() {
		stop();
		start();
	}

}
