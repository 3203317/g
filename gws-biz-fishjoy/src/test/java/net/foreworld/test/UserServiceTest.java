package net.foreworld.test;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.foreworld.service.FishjoyService;

/**
 *
 * @author huangxin
 *
 */
public class UserServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Resource
	private FishjoyService userService;

}