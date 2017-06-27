package net.foreworld.test;

import javax.annotation.Resource;

import net.foreworld.service.UserService;

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

}