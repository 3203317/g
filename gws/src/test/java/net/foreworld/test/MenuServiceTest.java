package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.model.Menu;
import net.foreworld.model.ResultMap;
import net.foreworld.service.MenuService;

import org.junit.Assert;
import org.junit.Test;

public class MenuServiceTest extends BasicTest {

	@Resource
	private MenuService menuService;

	@Test
	public void test_findByMenu() {
		List<Menu> list = menuService.findByMenu(null, 1, Integer.MAX_VALUE);
		System.out.println("-----" + list.size());
	}

	@Test
	public void test_findByPid() {
		List<Menu> list = menuService.findByPid("22");
		System.out.println("-----" + list.size());
	}

	@Test
	public void test_saveNew() {
		Menu menu = new Menu();
		menu.setId("id");
		menu.setPid("1");
		menu.setPath("path");
		menu.setMenu_name("menu_name");
		menu.setMenu_url("menu_url");
		menu.setSort(123456);
		menu.setIs_parent(1);

		ResultMap<Menu> map = menuService.saveNew(menu);

		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_editInfo() {
		Menu menu = new Menu();
		menu.setId("id");
		menu.setPid("1");
		menu.setPath("2");
		menu.setMenu_name("3");
		menu.setMenu_url("4");
		menu.setSort(654321);
		menu.setIs_parent(0);

		ResultMap<Void> map = menuService.editInfo(menu);

		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

	@Test
	public void test_remove() {
		ResultMap<Void> map = menuService.remove("id");
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}