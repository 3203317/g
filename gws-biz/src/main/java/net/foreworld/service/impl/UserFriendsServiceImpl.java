package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import net.foreworld.model.ResultMap;
import net.foreworld.model.UserFriends;
import net.foreworld.service.UserFriendsService;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("userFriendsService")
public class UserFriendsServiceImpl extends BaseService<UserFriends> implements UserFriendsService {

	@Override
	public int save(UserFriends entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(UserFriends entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<UserFriends> findMyFriends(String my_id) {
		return null;
	}

	@Override
	public ResultMap<Void> apply(UserFriends entity) {
		return null;
	}

	@Override
	public ResultMap<Void> checkFriends(String my_id, String friend_id) {
		return null;
	}

}
