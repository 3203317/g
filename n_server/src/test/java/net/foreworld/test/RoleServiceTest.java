package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import net.foreworld.model.Role;
import net.foreworld.service.RoleService;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

public class RoleServiceTest extends BasicTest {

	@Resource
	private RoleService roleService;

	@Test
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚,true时为回滚
	public void test_findByRole() {

		List<Role> list = roleService.findByRole(null, 1, Integer.MAX_VALUE);
		System.out.println(list.size());
	}
}