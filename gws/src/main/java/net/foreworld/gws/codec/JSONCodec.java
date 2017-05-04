package net.foreworld.gws.codec;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.foreworld.gws.model.ProtocolModel;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class JSONCodec extends MessageToMessageCodec<TextWebSocketFrame, ProtocolModel> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocolModel msg, List<Object> out) throws Exception {
		Gson gson = new Gson();
		out.add(new TextWebSocketFrame(gson.toJson(msg)));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		String text = msg.text();
		ProtocolModel model = new ProtocolModel();

		JsonObject jo = new JsonParser().parse(text).getAsJsonObject();

		if (jo.has("version")) {
			model.setVersion(jo.get("version").getAsInt());
		}

		if (jo.has("method")) {
			model.setMethod(jo.get("method").getAsInt());
		}

		if (jo.has("seqId")) {
			model.setSeqId(jo.get("seqId").getAsInt());
		}

		if (jo.has("timestamp")) {
			model.setTimestamp(jo.get("timestamp").getAsInt());
		}

		if (jo.has("data")) {
			model.setData(jo.get("data").getAsString());
		}

		out.add(model);
	}

}
