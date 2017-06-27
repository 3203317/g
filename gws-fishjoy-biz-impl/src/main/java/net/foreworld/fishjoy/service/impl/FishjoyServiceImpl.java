package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.foreworld.fishjoy.model.Bullet;
import net.foreworld.fishjoy.model.BulletBlast;
import net.foreworld.fishjoy.service.FishjoyService;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.impl.BaseService;

/**
 *
 * @author huangxin
 *
 */
@Service("fishjoyService")
public class FishjoyServiceImpl extends BaseService implements FishjoyService {

	private static final Logger logger = LoggerFactory.getLogger(FishjoyServiceImpl.class);

	@Override
	public ResultMap<List<Receiver<Bullet>>> shot(String server_id, String channel_id, Bullet bullet) {

		ResultMap<List<Receiver<Bullet>>> map = new ResultMap<List<Receiver<Bullet>>>();
		map.setSuccess(false);

		Receiver<Bullet> rec = new Receiver<Bullet>();
		rec.setServer_id(server_id);
		rec.setChannel_id(channel_id);
		rec.setData(bullet);

		List<Receiver<Bullet>> list = new ArrayList<Receiver<Bullet>>();
		list.add(rec);

		map.setData(list);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<List<Receiver<BulletBlast>>> blast(String server_id, String channel_id, BulletBlast bulletBlast) {
		// TODO Auto-generated method stub
		return null;
	}

}
