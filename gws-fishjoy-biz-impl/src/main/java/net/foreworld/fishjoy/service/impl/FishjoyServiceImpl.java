package net.foreworld.fishjoy.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.foreworld.fishjoy.service.FishjoyService;
import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
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
	public ResultMap<List<Receiver<String>>> shot(String server_id,
			String channel_id, String bullet) {

		ResultMap<List<Receiver<String>>> map = new ResultMap<List<Receiver<String>>>();
		map.setSuccess(false);

		Receiver<String> rec = new Receiver<String>();
		rec.setServer_id(server_id);
		rec.setChannel_id(channel_id);
		rec.setData(bullet);

		List<Receiver<String>> list = new ArrayList<Receiver<String>>();
		list.add(rec);

		map.setData(list);
		map.setSuccess(true);
		return map;
	}

}
