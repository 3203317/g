package net.foreworld.gws.util;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@EnableJms
public class LogConsumer {

	@JmsListener(destination="abc")
	public void received(String msg) {
		System.out.println(msg);
//		BytesMessage textMessage = (BytesMessage) msg;
//         try {
//        	 
//        	 
//        	 
//             
//             System.out.println(textMessage);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
         }

}