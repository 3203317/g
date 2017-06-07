package net.foreworld.mapper;

import java.util.List;
import java.util.Map;

import net.foreworld.model.UserFriends;

/**
 * 
 * @author huangxin
 *
 */
public interface UserFriendsMapper extends MyMapper<UserFriends> {

	List<UserFriends> findByMyFriends(Map<String, Object> map);
}