package net.foreworld.gws.plugin;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class Front {

	@Value("${protocol.version}")
	private int protocol_version;

	@Value("${queue.back.send}")
	private String queue_back_send;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	private static final Logger logger = LoggerFactory.getLogger(Front.class);

	@JmsListener(destination = "${queue.front.start}")
	public void start(TextMessage msg) {

		try {
			logger.info("front amq start: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}

	@JmsListener(destination = "${queue.front.stop}")
	public void stop(TextMessage msg) {

		try {
			logger.info("front amq stop: {}", msg.getText());
		} catch (JMSException e) {
			logger.error("", e);
		}
	}
}