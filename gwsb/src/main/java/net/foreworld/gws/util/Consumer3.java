package net.foreworld.gws.util;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.model.User;

@Component
public class Consumer3 {
	

//    @JmsListener(destination = "channel.send" )
//    public void receiveQueue2(String text) {
//       System.out.println("Consumer4="+text);
//    }
    @JmsListener(destination = "channel.send" )
    public void receiveQueue2(BytesMessage text) {
       System.out.println("Consumer4="+text);

       try {
       BytesMessage byteMsg = (BytesMessage) text;
		int len = (int) byteMsg.getBodyLength();
		byte[] data = new byte[len];						
		byteMsg.readBytes(data);	
       
		

		try {
			Method.RequestProtobuf _user = Method.RequestProtobuf.parseFrom(data);
			System.out.println(_user.getMethod());
			System.out.println(_user.getData());
			

			User.UserProtobuf _user2 = User.UserProtobuf.parseFrom(_user.getData());
			System.out.println(_user2.getUserName());
			System.out.println(_user2.getUserPass());
		} catch (InvalidProtocolBufferException e) {
		}
       
		System.out.println(text.getBodyLength());
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}