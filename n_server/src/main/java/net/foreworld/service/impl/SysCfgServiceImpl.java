package net.foreworld.service.impl;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;
import net.foreworld.service.SysCfgService;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author Administrator
 *
 */
@Service("sysCfgService")
public class SysCfgServiceImpl extends BaseService<SysCfg> implements
		SysCfgService {

	@Override
	public List<SysCfg> findBySysCfg(SysCfg sysCfg, int page, int rows) {
		Example example = new Example(SysCfg.class);
		example.setOrderByClause("create_time ASC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<Void> editInfo(SysCfg sysCfg) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		SysCfg _sysCfg = new SysCfg();
		_sysCfg.setKey_(sysCfg.getKey_());
		_sysCfg.setValue_(sysCfg.getValue_());
		updateNotNull(_sysCfg);

		map.setSuccess(true);
		return map;
	}

}
