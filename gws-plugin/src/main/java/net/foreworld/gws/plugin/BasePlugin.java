package net.foreworld.gws.plugin;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import com.google.protobuf.InvalidProtocolBufferException;

import net.foreworld.gws.protobuf.Common.SenderProtobuf;

/**
 * 
 * @author huangxin
 *
 */
public abstract class BasePlugin {

	public SenderProtobuf read(BytesMessage msg) throws JMSException, InvalidProtocolBufferException {

		int len = (int) msg.getBodyLength();
		byte[] data = new byte[len];
		msg.readBytes(data);

		return SenderProtobuf.parseFrom(data);
	}

}