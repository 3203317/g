package net.foreworld.gws.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.gws.util.ChannelUtil;
import net.foreworld.util.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin
 *
 */
@PropertySource("classpath:redis.properties")
@PropertySource("classpath:server.properties")
@Component
@Sharable
public class UnRegChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UnRegChannelHandler.class);

	@Value("${server.id}")
	private String server_id;

	@Value("${sha.channel.close}")
	private String sha_channel_close;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("", cause);
		ctx.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelUnregistered");

		String channel_id = ctx.channel().id().asLongText();
		// 删除通道
		ChannelUtil.getDefault().removeChannel(channel_id);
		closeChannel(channel_id);
		super.channelUnregistered(ctx);
	}

	private void closeChannel(String channel_id) {

		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return;

		List<String> s = new ArrayList<String>();
		s.add("server_id");
		s.add("channel_id");

		List<String> b = new ArrayList<String>();
		b.add(server_id);
		b.add(channel_id);

		j.evalsha(sha_channel_close, s, b);
		j.close();

	}

}
