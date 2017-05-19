package net.foreworld.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public final class ActiveMQUtil {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMQUtil.class);

	private static String USER;
	private static String PASS;
	private static String URL;

	@Value("${db.user}")
	public void setUser(String user) {
		USER = user;
	}

	@Value("${db.pass}")
	public void setPass(String pass) {
		PASS = pass;
	}

	@Value("${db.url}")
	public void setUrl(String url) {
		URL = url;
	}

	private static Connection conn = null;

	// 定义一个静态私有变量(不初始化，不使用final关键字，使用volatile保证了多线程访问时instance变量的可见性，避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用)
	private static volatile ActiveMQUtil instance;

	private ActiveMQUtil() {
	}

	/**
	 * 定义一个共有的静态方法，返回该类型实例
	 *
	 * @return
	 */
	public static ActiveMQUtil getDefault() {
		// 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
		if (null == instance) {
			// 同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
			synchronized (ActiveMQUtil.class) {
				// 未初始化，则初始instance变量
				if (null == instance) {
					instance = new ActiveMQUtil();
				}
			}
		}
		return instance;
	}

	private void init() {
		ConnectionFactory factory = new ActiveMQConnectionFactory(USER, PASS, URL);

		try {
			conn = factory.createConnection();
			conn.start();
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Connection getConn() {
		if (null == conn) {
			init();
		}

		return conn;
	}

	public void close() {
		if (null != conn) {
			try {
				conn.close();
			} catch (JMSException e) {
				logger.error("", e);
			}
		}
	}
}
