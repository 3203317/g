package net.foreworld.gws.util;


import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@EnableJms
public class LogConsumer2 {

	@JmsListener(destination = "sample.topicl")
    public void receiveQueue(String text) {
		
		
		
       System.out.println("Consumer2"+ text);
    }
	}