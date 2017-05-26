package net.foreworld.gws.server;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Server {

	public enum Status {
		START(1), STOP(0);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public abstract void start();

	public abstract void shutdown();

	public void restart() {
		shutdown();
		start();
	}

}
