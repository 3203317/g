package net.foreworld.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.foreworld.mapper.UserFriendsMapper;
import net.foreworld.model.UserFriends;
import net.foreworld.service.UserFriendsService;

/**
 * 
 * @author huangxin
 *
 */
@Service("userFriendsService")
public class UserFriendsServiceImpl extends BaseService<UserFriends> implements UserFriendsService {

	@Override
	public int updateNotNull(UserFriends entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public int save(UserFriends entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public List<UserFriends> findByMyFriends(String my_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("my_id", my_id);
		return ((UserFriendsMapper) mapper).findByMyFriends(map);
	}

}
