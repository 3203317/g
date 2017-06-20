package net.foreworld.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.foreworld.model.ResultMap;
import net.foreworld.service.UserService;

/**
 *
 * @author huangxin
 *
 */
public class UserServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Resource
	private UserService userService;

	@Test
	public void test_findByRole() {
		ResultMap<Void> map = userService.logout("server_id", "channel_id");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}