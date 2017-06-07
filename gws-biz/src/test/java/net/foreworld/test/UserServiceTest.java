package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import net.foreworld.model.User;
import net.foreworld.service.UserService;

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

}