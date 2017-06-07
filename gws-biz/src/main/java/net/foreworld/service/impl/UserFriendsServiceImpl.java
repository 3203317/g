package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.UserFriends;
import net.foreworld.service.UserFriendsService;
import tk.mybatis.mapper.entity.Example;

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
		Example example = new Example(UserFriends.class);
		example.setOrderByClause("create_time DESC");

		PageHelper.startPage(1, Integer.MAX_VALUE);
		return selectByExample(example);
	}

}
