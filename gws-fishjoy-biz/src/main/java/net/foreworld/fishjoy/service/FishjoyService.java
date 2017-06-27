package net.foreworld.fishjoy.service;

import net.foreworld.fishjoy.model.Bullet;
import net.foreworld.fishjoy.model.BulletBlast;
import net.foreworld.model.ResultMap;
import net.foreworld.model.SameData;
import net.foreworld.service.IService;

/**
 *
 * @author huangxin
 *
 */
public interface FishjoyService extends IService {

	/**
	 *
	 * @param server_id
	 * @param channel_id
	 * @param bullet
	 * @return
	 */
	ResultMap<SameData<Bullet>> shot(String server_id, String channel_id,
			Bullet bullet);

	/**
	 *
	 * @param server_id
	 * @param channel_id
	 * @param bulletBlast
	 * @return
	 */
	ResultMap<SameData<BulletBlast>> blast(String server_id, String channel_id,
			BulletBlast bulletBlast);

}