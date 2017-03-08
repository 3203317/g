package net.foreworld.test;

import javax.annotation.Resource;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
import net.foreworld.service.UserService;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest extends BasicTest {

	@Resource
	private UserService userService;

	@Test
	public void test_changePwd() {
		ResultMap<Void> map = userService.changePwd("id", "123456", "1");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_login() {
		ResultMap<User> map = userService.login("user_name", "1");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}