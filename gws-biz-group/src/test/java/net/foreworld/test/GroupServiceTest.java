package net.foreworld.test;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

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
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_findByRole() {
		ResultMap<String> map = groupService.search("user_id", "group_type", "group_id");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}