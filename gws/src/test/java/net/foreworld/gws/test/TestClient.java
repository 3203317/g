package net.foreworld.gws.test;

/**
 *
 * @author huangxin
 *
 */
public class TestClient {

	public static void main(String[] args) {

		int port = 9988;
		if (null != args && 0 < args.length) {
			port = Integer.valueOf(args[0]);
		}

	}

}
