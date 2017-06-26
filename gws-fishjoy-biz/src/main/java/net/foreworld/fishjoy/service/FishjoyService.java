package net.foreworld.fishjoy.service;

import java.util.List;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
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
	ResultMap<List<Receiver<String>>> shot(String server_id, String channel_id,
			String bullet);

}