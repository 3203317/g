package net.foreworld.gws.codec.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import net.foreworld.gws.model.Login;

/**
 *
 * @author huangxin
 *
 */
public class MsgEncoder extends MessageToMessageEncoder<Login> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Login msg, List<Object> out)
			throws Exception {

	}

}
