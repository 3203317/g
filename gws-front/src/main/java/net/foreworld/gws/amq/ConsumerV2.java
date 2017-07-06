package net.foreworld.gws.amq;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.channel.Channel;
import net.foreworld.gws.util.ChannelUtil;
import net.foreworld.gws.util.Constants;
import net.foreworld.util.StringUtil;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:activemq.properties")
@Component
public class ConsumerV2 {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerV2.class);

	@JmsListener(destination = "${queue.back.send.v2}" + "." + "${server.id}")
	public void back_send(BytesMessage msg) {

		try {
			byte[] data = new byte[(int) msg.getBodyLength()];
			msg.readBytes(data);

			String s = new String(data, Charsets.UTF_8);

			JsonObject jo = new JsonParser().parse(s).getAsJsonObject();

			JsonElement joo = jo.get("receiver");

			if (null == joo)
				return;

			String _receiver = StringUtil.isEmpty(joo.getAsString());

			if (null == _receiver)
				return;

			joo = jo.get("data");

			if (null == joo)
				return;

			String _data = StringUtil.isEmpty(joo.getAsString());

			if (null == _data)
				return;

			if (Constants.ALL.equals(_receiver)) {
				ChannelUtil.getDefault().broadcast(_data);
				return;
			}

			Channel c = ChannelUtil.getDefault().getChannel(_receiver);

			if (null != c)
				c.writeAndFlush(_data);

		} catch (JMSException e) {
			logger.error("", e);
		}
	}

}