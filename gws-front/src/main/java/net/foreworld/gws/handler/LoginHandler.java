package net.foreworld.gws.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.foreworld.gws.protobuf.Common.RequestProtobuf;
import net.foreworld.gws.protobuf.User;
import net.foreworld.gws.util.ChannelUtil;
import net.foreworld.gws.util.Constants;
import net.foreworld.gws.util.RedisUtil;
import net.foreworld.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:redis.properties")
@Component
@Sharable
public class LoginHandler extends SimpleChannelInboundHandler<RequestProtobuf> {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginHandler.class);

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
	protected void channelRead0(ChannelHandlerContext ctx,
			final RequestProtobuf msg) throws Exception {
		logger.info("{}:{}:{}:{}", msg.getVersion(), msg.getMethod(),
				msg.getSeqId(), msg.getTimestamp());

		try {

			User.LoginProtobuf req = User.LoginProtobuf
					.parseFrom(msg.getData());
			String code = req.getCode();

			final Channel channel = ctx.channel();

			final String channel_id = channel.id().asLongText();

			if (verify(code, channel_id)) {

				ctx.pipeline().replace(this, "unReg", unRegChannelHandler);

				ChannelUtil.getDefault().putChannel(channel_id, channel);
				jmsMessagingTemplate.convertAndSend(queue_channel_open,
						server_id + "::" + channel_id);
				logger.info("channel amq open: {}:{}", server_id, channel_id);

				ctx.flush();
				return;
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error("", e);
		}

		ChannelFuture future = ctx.close();

		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
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
	 * @param channel_id
	 * @return
	 */
	private boolean verify(String code, String channel_id) {

		code = StringUtil.isEmpty(code);

		if (null == code) {
			return false;
		}

		List<String> s = new ArrayList<String>();
		s.add("code");
		s.add("channel_id");
		s.add("seconds");
		s.add("server_id");

		List<String> b = new ArrayList<String>();
		b.add(code);
		b.add(channel_id);
		b.add(String.valueOf(sha_token_expire));
		b.add(server_id);

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return false;

		Object o = j.evalsha(sha_token, s, b);
		j.close();

		return Constants.OK.equals(o);
	}

}
