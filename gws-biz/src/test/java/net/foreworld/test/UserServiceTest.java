package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import net.foreworld.model.ResultMap;
import net.foreworld.model.User;
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
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_findByUser() {
		List<User> list = userService.findByUser(null, 1, Integer.MAX_VALUE);
		logger.debug("{}", list.size());
	}

	@Test
	public void test_register() {

		User entity = new User();
		entity.setUser_name("hxi");
		entity.setUser_pass("123456");

		ResultMap<User> map = userService.register(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}