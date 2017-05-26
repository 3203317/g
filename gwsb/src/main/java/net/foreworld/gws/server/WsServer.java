package net.foreworld.gws.server;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:server.properties")
@Component
public class WsServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(WsServer.class);

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}

	@Bean
	public Topic topic() {
		return new ActiveMQTopic("sample.topic");
	}

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@Override
	public void start() {

		jmsMessagingTemplate.convertAndSend(topic, "hi,activeMQ(topic)");

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				beforeShut();
				shutdown();
			}
		});
	}

	@Override
	public void shutdown() {
	}

	private void beforeShut() {
	}

}
