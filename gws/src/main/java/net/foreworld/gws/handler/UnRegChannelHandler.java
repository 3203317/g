package net.foreworld.gws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.foreworld.util.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin
 *
 */
@Component
@Sharable
public class UnRegChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UnRegChannelHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("{}", cause);
		ctx.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelUnregistered");
		remove(ctx.channel().id().asLongText());
		super.channelUnregistered(ctx);
	}

	/**
	 * exit and clean
	 * 
	 * @param id
	 * @return
	 */
	private boolean remove(String id) {
		Jedis j = RedisUtil.getDefault().getJedis();

		if (null == j)
			return false;

		logger.info("id: {}", id);

		j.close();
		return true;
	}

}
