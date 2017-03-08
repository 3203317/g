package net.foreworld.test;

import javax.annotation.Resource;

import net.foreworld.model.Manager;
import net.foreworld.model.ResultMap;
import net.foreworld.service.ManagerService;

import org.junit.Assert;
import org.junit.Test;

public class ManagerServiceTest extends BasicTest {

	@Resource
	private ManagerService managerService;

	@Test
	public void test_login() {
		ResultMap<Manager> map = managerService.login("admin", "1");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_changePwd() {
		ResultMap<Void> map = managerService.changePwd("admin", "1", "123456");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}