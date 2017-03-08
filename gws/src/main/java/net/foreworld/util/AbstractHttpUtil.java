package net.foreworld.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 *
 */
public abstract class AbstractHttpUtil {

	/**
	 * 新增Cookie
	 *
	 * @param res
	 * @param domain
	 *            .baidu.com
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie存放时间（单位为秒；3天：3*24*60*60；值为0，则cookie随浏览器关闭而清除）
	 */
	public static void addCookie(HttpServletResponse res, String domain,
			String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (null != StringUtil.isEmpty(domain))
			cookie.setDomain(domain);
		cookie.setPath("/");
		if (0 < maxAge)
			cookie.setMaxAge(maxAge);
		res.addCookie(cookie);
	}

	/**
	 * 获取Cookie的值
	 *
	 * @param req
	 * @param name
	 *            cookie名称
	 * @return
	 */
	public static String getCookie(HttpServletRequest req, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(req);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie.getValue();
		}
		return null;
	}

	protected static Map<String, Cookie> readCookieMap(
			HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			} // End
		}
		return cookieMap;
	}

	/**
	 * 获取应用动态域名
	 *
	 * @param req
	 * @return
	 */
	public static String getDynamicName(HttpServletRequest req) {
		String dyName = req.getLocalName() + ":" + req.getLocalPort()
				+ req.getContextPath();
		return dyName;
	}

	/**
	 * 获取客户端真实IP
	 *
	 * @param req
	 * @return
	 */
	public static String getClientRealIP(HttpServletRequest req) {
		String ipAddr = req.getHeader("x-forwarded-for");

		if (null == StringUtil.isEmpty(ipAddr)
				|| "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = req.getHeader("Proxy-Client-IP");
		}
		if (null == StringUtil.isEmpty(ipAddr)
				|| "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = req.getHeader("WL-Proxy-Client-IP");
		}
		if (null == StringUtil.isEmpty(ipAddr)
				|| "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = req.getRemoteAddr();

			if ("127.0.0.1".equals(ipAddr)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
					return null;
				}
				ipAddr = inet.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (null != ipAddr && 15 < ipAddr.length()) { // "***.***.***.***".length()
			if (0 < ipAddr.indexOf(",")) {
				ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
			}
		}
		return ipAddr;
	}

	/**
	 * 获取地址后缀，.html
	 *
	 * @param req
	 * @return
	 */
	public static String getUrlSuffix(HttpServletRequest req) {
		String url = req.getRequestURI();

		return -1 == url.indexOf(".") ? null : url.substring(
				url.lastIndexOf(".") + 1, url.length());
	}
}
