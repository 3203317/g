package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import net.foreworld.model.Group;
import net.foreworld.service.GroupService;

/**
 * 
 * @author huangxin
 *
 */
public class GroupServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(GroupServiceTest.class);

	@Resource
	private GroupService roleService;

	@Test
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_findByRole() {
		List<Group> list = roleService.findByRole(null, 1, Integer.MAX_VALUE);
		logger.debug("{}", list.size());
	}

}