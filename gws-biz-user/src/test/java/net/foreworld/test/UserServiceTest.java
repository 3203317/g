package net.foreworld.test;

import javax.annotation.Resource;

import net.foreworld.model.ResultMap;
import net.foreworld.service.UserService;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin
 *
 */
public class UserServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceTest.class);

	@Resource
	private UserService userService;

	@Test
	public void test_findByRole() {
		ResultMap<String> map = userService.logout("user_id");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}