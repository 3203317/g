package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import net.foreworld.model.ResultMap;
import net.foreworld.model.Role;
import net.foreworld.service.RoleService;

public class RoleServiceTest extends BasicTest {

	@Resource
	private RoleService roleService;

	@Test
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_findByRole() {
		List<Role> list = roleService.findByRole(null, 1, Integer.MAX_VALUE);
		System.out.println("-----" + list.size());
	}

	@Test
	public void test_saveNew() {
		Role entity = new Role();
		entity.setRole_name("role_name");
		entity.setRole_desc("role_desc");
		entity.setStatus(RoleService.Status.START.value());

		ResultMap<Role> map = roleService.saveNew(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_editInfo() {
		Role entity = new Role();
		entity.setId("ce2b91715e884800ad24bb5acba8cce2");
		entity.setRole_desc("123456");
		entity.setStatus(RoleService.Status.STOP.value());

		ResultMap<Void> map = roleService.editInfo(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_setStatus() {
		ResultMap<Void> map = roleService.setStatus("ce2b91715e884800ad24bb5acba8cce2", RoleService.Status.STOP);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}