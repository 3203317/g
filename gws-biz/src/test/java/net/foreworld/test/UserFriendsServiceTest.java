package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.foreworld.model.ResultMap;
import net.foreworld.model.UserFriends;
import net.foreworld.service.UserFriendsService;

/**
 * 
 * @author huangxin
 *
 */
public class UserFriendsServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(UserFriendsServiceTest.class);

	@Resource
	private UserFriendsService userFriendsService;

	@Test
	public void test_getMyFriends() {
		List<UserFriends> list = userFriendsService.getMyFriends("1");
		logger.debug("{}", list.size());
	}

	@Test
	public void test_apply() {

		UserFriends entity = new UserFriends();
		entity.setFriend_a("hx");
		entity.setFriend_b("wy");
		entity.setApply_content("i want");

		ResultMap<Void> map = userFriendsService.apply(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}