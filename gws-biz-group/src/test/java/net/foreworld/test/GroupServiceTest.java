package net.foreworld.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;

/**
 *
 * @author huangxin
 *
 */
public class GroupServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceTest.class);

	@Resource
	private GroupService groupService;

	@Test
	public void test_findByRole() {
		ResultMap<String> map = groupService.search("user_id", "group_type");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}