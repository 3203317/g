package net.foreworld.fishjoy.service.impl;

import net.foreworld.fishjoy.model.Bullet;
import net.foreworld.fishjoy.model.BulletBlast;
import net.foreworld.fishjoy.service.FishjoyService;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.impl.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author huangxin
 *
 */
@Service("fishjoyService")
public class FishjoyServiceImpl extends BaseService implements FishjoyService {

	private static final Logger logger = LoggerFactory
			.getLogger(FishjoyServiceImpl.class);

	@Override
	public ResultMap<SameData<Bullet>> shot(String server_id,
			String channel_id, Bullet bullet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<SameData<BulletBlast>> blast(String server_id,
			String channel_id, BulletBlast bulletBlast) {
		// TODO Auto-generated method stub
		return null;
	}

}
