package net.foreworld.gws;

/**
 *
 * @author huangxin
 *
 */
public abstract class Server {

	protected void start() {

	}

	protected void stop() {

	}

	protected void restart() {
		stop();
		start();
	}

}
