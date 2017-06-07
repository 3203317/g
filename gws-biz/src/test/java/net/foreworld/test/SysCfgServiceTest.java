package net.foreworld.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;
import net.foreworld.service.SysCfgService;

/**
 * 
 * @author huangxin
 *
 */
public class SysCfgServiceTest extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger(SysCfgServiceTest.class);

	@Resource
	private SysCfgService sysCfgService;

	@Test
	@Transactional
	@Rollback(false)
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_findBySysCfg() {
		List<SysCfg> list = sysCfgService.findBySysCfg(null, 1, Integer.MAX_VALUE);
		logger.debug("{}", list.size());
	}

	@Test
	// 标明使用完此方法后事务不回滚, true时为回滚
	public void test_editInfo() {
		SysCfg entity = new SysCfg();

		entity.setKey_("1");
		entity.setValue_("2");
		entity.setComment("3");
		entity.setTitle("4");
		entity.setCreate_time(new Date());

		ResultMap<Void> map = sysCfgService.editInfo(entity);

		Assert.assertTrue(map.getMsg(), map.getSuccess());

	}

}