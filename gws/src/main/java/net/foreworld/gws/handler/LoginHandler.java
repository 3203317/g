package net.foreworld.gws.handler;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.foreworld.gws.protobuf.Method;
import net.foreworld.gws.protobuf.Method.RequestProtobuf;
import net.foreworld.gws.protobuf.method.user.Login;
import net.foreworld.gws.util.ChannelUtil;
import net.foreworld.gws.util.Constants;
import net.foreworld.util.RedisUtil;
import net.foreworld.util.StringUtil;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:server.properties")
@PropertySource("classpath:redis.properties")
@Component
@Sharable
public class LoginHandler extends SimpleChannelInboundHandler<Method.RequestProtobuf> {

	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	@Value("${sha.token}")
	private String sha_token;

	@Value("${sha.token.expire}")
	private int sha_token_expire;

	@Value("${server.id}")
	private String server_id;

	@Value("${queue.channel.open}")
	private String queue_channel_open;

	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Resource(name = "unRegChannelHandler")
	private UnRegChannelHandler unRegChannelHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestProtobuf msg) throws Exception {
		logger.info("{}:{}:{}:{}", msg.getVersion(), msg.getMethod(), msg.getSeqId(), msg.getTimestamp());

		if (95 == msg.getMethod()) {

			try {

				Login.RequestProtobuf req = Login.RequestProtobuf.parseFrom(msg.getData());
				String code = req.getCode();

				String channel_id = ctx.channel().id().asLongText();

				String token = verify(code, channel_id);

				logger.info("{} : {}", code, token);

				if (null != token) {

					ctx.pipeline().replace(this, "unReg", unRegChannelHandler);

					ChannelUtil.getDefault().putChannel(channel_id, ctx.channel());
					jmsMessagingTemplate.convertAndSend(queue_channel_open, server_id + ":" + channel_id);
					logger.info("channel open {}:{}", server_id, channel_id);

					Method.ResponseProtobuf.Builder resp = Method.ResponseProtobuf.newBuilder();

					resp.setVersion(msg.getVersion());
					resp.setMethod(msg.getMethod());
					resp.setSeqId(msg.getSeqId());
					resp.setTimestamp(System.currentTimeMillis());

					Login.ResponseProtobuf.Builder data = Login.ResponseProtobuf.newBuilder();
					data.setToken(token);

					resp.setData(data.build().toByteString());

					ctx.writeAndFlush(resp);
					return;
				}

			} catch (InvalidProtocolBufferException e) {
				logger.error("", e);
			}
		}

		ChannelFuture future = ctx.close();

		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				SocketAddress addr = ctx.channel().remoteAddress();

				if (future.isSuccess()) {
					logger.info("ctx close: {}", addr);
					return;
				}

				logger.info("ctx close failure: {}", addr);
				ctx.close();
			}
		});
	}

	/**
	 *
	 * @param code
	 * @param uuid
	 * @return
	 */
	private String verify(String code, String uuid) {

		code = StringUtil.isEmpty(code);

		if (null == code) {
			return null;
		}

		List<String> s = new ArrayList<String>();
		s.add("code");
		s.add("access_token");
		s.add("seconds");
		s.add("server_id");

		List<String> b = new ArrayList<String>();
		b.add(code);
		b.add(uuid);
		b.add(String.valueOf(sha_token_expire));
		b.add(server_id);

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return null;

		Object o = j.evalsha(sha_token, s, b);
		j.close();

		if (null == o || !Constants.OK.equals(o)) {
			return null;
		}

		return uuid;
	}

}
