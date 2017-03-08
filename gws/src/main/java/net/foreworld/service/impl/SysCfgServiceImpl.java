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
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("sysCfgService")
public class SysCfgServiceImpl extends BaseService<SysCfg> implements
		SysCfgService {

	@Override
	public int updateNotNull(SysCfg entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<SysCfg> findBySysCfg(SysCfg entity, int page, int rows) {
		Example example = new Example(SysCfg.class);
		example.setOrderByClause("key_ ASC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<Void> editInfo(SysCfg entity) {
		ResultMap<Void> map = new ResultMap<Void>();
		updateNotNull(entity);
		map.setSuccess(true);
		return map;
	}

}
