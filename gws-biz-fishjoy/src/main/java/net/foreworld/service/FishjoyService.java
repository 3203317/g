package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Bullet;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;

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
	ResultMap<List<Receiver<Bullet>>> shot(String server_id, String channel_id, Bullet bullet);

}