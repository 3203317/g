package net.foreworld.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;
import net.foreworld.service.SysCfgService;

public class SysCfgServiceTest extends BasicTest {

	@Resource
	private SysCfgService sysCfgService;

	@Test
	public void test_findBySysCfg() {
		List<SysCfg> list = sysCfgService.findBySysCfg(null, 1, Integer.MAX_VALUE);
		System.out.println("-----" + list.size());
	}

	@Test
	public void test_editInfo() {
		SysCfg entity = new SysCfg();
		entity.setKey_("01");
		entity.setValue_("02");
		entity.setTitle("03");
		entity.setComment("04");

		ResultMap<Void> map = sysCfgService.editInfo(entity);
		Assert.assertTrue(map.getMsg(), map.getSuccess());
	}

}
