package net.foreworld.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestT extends BasicTest {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	@Test
	public void testAdd() {
		// SmSWebService service = new SmSWebService();
		// SmSWebServiceSoap serviceSoap = service.getSmSWebServiceSoap();
		// WsSendResponse response = serviceSoap.sendSms("154", "MOMO668",
		// "123456", "18530053050", "您本次验证码:1234，感谢您的支持，祝您生活愉快！！", "", "");
		//
		// System.err.println(response.getReturnStatus());

		// int i = (int) ((Math.random() * 5 + 1) * 1000);
		// String id = String.valueOf(i);
		// if (4 < id.length()) {
		// id = id.substring(0, 4);
		// }
		// System.out.println(id);

		// System.out.println(5000 / 100);

		String a = "null";
		String b = null;

		System.out.println(a.equals(b));

		System.out.println(sdf.format(new Date()));

		String s = "M08151306_20160528155.853.jpg";
		int i = s.lastIndexOf(".");
		if (-1 == i) {

		}
		System.out.println((-1 == i) ? "" : s.substring(i));
	}
}