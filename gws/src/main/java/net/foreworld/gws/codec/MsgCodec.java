package net.foreworld.gws.codec;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.foreworld.gws.model.Msg;

/**
 * 
 * @author huangxin
 *
 */
public class MsgCodec extends MessageToMessageCodec<TextWebSocketFrame, Msg> {

	private final ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, List<Object> out) throws Exception {
		ObjectNode root = jsonMapper.createObjectNode();

		JsonNode body = jsonMapper.readTree(msg.getBody());

		root.put("version", msg.getVersion());
		root.put("method", msg.getMethod());
		root.put("seqId", msg.getSeqId());
		root.put("token", msg.getToken());
		root.set("body", body);

		out.add(new TextWebSocketFrame(root.toString()));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		String text = msg.text();
		JsonNode root = jsonMapper.readTree(text);
		Msg m = new Msg();
		if (root.has("version")) {
			m.setVersion(root.get("version").shortValue());
		}
		if (root.has("method")) {
			m.setMethod(root.get("method").shortValue());
		}
		if (root.has("seqId")) {
			m.setSeqId(root.get("seqId").asInt());
		}
		if (root.has("token")) {
			m.setToken(root.get("token").asLong());
		}
		if (root.has("body")) {
			m.setBody(root.get("body").toString().getBytes());
		}
		out.add(m);
	}

}
