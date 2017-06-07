package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public void test_findByMyFriends() {
		List<UserFriends> list = userFriendsService.findByMyFriends("121");
		logger.debug("{}", list.size());
	}

}