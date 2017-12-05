package net.foreworld.util;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public abstract class Server extends Client {

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

}
