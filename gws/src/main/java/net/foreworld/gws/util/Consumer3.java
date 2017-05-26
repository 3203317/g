package net.foreworld.gws.util;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class Consumer3 {
    @JmsListener(destination = "sample.topic" )
    public void receiveQueue(String text) {
       System.out.println("Consumer3="+text);
    }
    

}