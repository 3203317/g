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

	List<UserFriends> getMyFriends(Map<String, Object> map);
}