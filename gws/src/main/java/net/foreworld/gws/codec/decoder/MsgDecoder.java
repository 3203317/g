package net.foreworld.gws.codec.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import net.foreworld.gws.model.Login;

/**
 *
 * @author huangxin
 *
 */
public class MsgDecoder extends MessageToMessageDecoder<Login> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Login msg, List<Object> out)
			throws Exception {

	}

}
