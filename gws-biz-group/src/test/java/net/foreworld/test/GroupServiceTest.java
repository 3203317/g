package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.model.Receiver;
import net.foreworld.model.ResultMap;
import net.foreworld.service.GroupService;

import org.junit.Assert;
import org.junit.Test;
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

	@Test
	public void test_findByRole() {
		ResultMap<List<Receiver<String>>> map = groupService.search(
				"server_id", "channel_id", "group_type");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}