package net.foreworld.gws.plugin;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import net.foreworld.gws.protobuf.Common.SenderProtobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * @author huangxin
 *
 */
public abstract class BasePlugin {

	public SenderProtobuf read(BytesMessage msg) throws JMSException,
			InvalidProtocolBufferException {

		int len = (int) msg.getBodyLength();
		byte[] data = new byte[len];
		msg.readBytes(data);

		return SenderProtobuf.parseFrom(data);
	}

}