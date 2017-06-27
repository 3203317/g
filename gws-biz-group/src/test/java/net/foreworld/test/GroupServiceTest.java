package net.foreworld.test;

import javax.annotation.Resource;

import net.foreworld.service.GroupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huangxin
 *
 */
public class GroupServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory
			.getLogger(GroupServiceTest.class);

	@Resource
	private GroupService groupService;

}