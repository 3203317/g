package net.foreworld.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.StringUtils;

/**
 * 
 * @author huangxin <3203317@qq.com>
 *
 */
public final class GzipUtil {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] compress(String str) {
		return compress(str, CharEncoding.UTF_8);
	}

	/**
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static byte[] compress(String str, String encoding) {
		if (null == str || 0 == str.length()) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes(encoding));
			gzip.close();
		} catch (IOException e) {
		}

		return out.toByteArray();
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] uncompress(byte[] bytes) {
		if (null == bytes || 0 == bytes.length) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);

		try {
			GZIPInputStream ungzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;

			while (0 <= (n = ungzip.read(buffer))) {
				out.write(buffer, 0, n);
			}

			return out.toByteArray();
		} catch (IOException e) {
		}

		return null;
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String uncompressToString(byte[] bytes) {
		return uncompressToString(bytes, CharEncoding.UTF_8);
	}

	/**
	 * 
	 * @param bytes
	 * @param encoding
	 * @return
	 */
	public static String uncompressToString(byte[] bytes, String encoding) {
		if (null == bytes || 0 == bytes.length) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);

		try {
			GZIPInputStream ungzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;

			while (0 <= (n = ungzip.read(buffer))) {
				out.write(buffer, 0, n);
			}

			return out.toString(encoding);
		} catch (IOException e) {
		}

		return null;
	}

	public static void main(String[] args) {
		// String str =
		// "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";
		String str = "%5B%7B%2s啥宋德福2lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221啥h打饭";
		System.out.println("原长度：" + str.length());

		byte[] b = GzipUtil.compress(str);

		System.out.println(b.toString());

		System.out.println("压缩后字符串：" + b.toString().length());
		System.out.println("解压缩后字符串：" + StringUtils.newStringUtf8(GzipUtil.uncompress(b)));
		System.out.println("解压缩后字符串：" + GzipUtil.uncompressToString(b));
	}
}
