package net.foreworld.service.impl;

import java.util.List;
import java.util.UUID;

import net.foreworld.model.Menu;
import net.foreworld.model.ResultMap;
import net.foreworld.service.MenuService;
import net.foreworld.util.StringUtil;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("menuService")
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	@Override
	public int updateNotNull(Menu entity) {
		entity.setPid(null);
		entity.setPath(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<Menu> findByMenu(Menu entity, int page, int rows) {
		Example example = new Example(Menu.class);
		example.setOrderByClause("sort ASC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public List<Menu> findByPid(String pid) {
		pid = StringUtil.isEmpty(pid);
		if (null == pid) {
			return null;
		}

		Example example = new Example(Menu.class);
		example.setOrderByClause("sort ASC");

		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pid", pid);

		return selectByExample(example);
	}

	@Override
	public ResultMap<Menu> saveNew(Menu entity) {
		ResultMap<Menu> map = new ResultMap<Menu>();
		map.setSuccess(false);

		// 获取父节点
		Menu pMenu = getById(entity.getPid());

		// 判断父节点是否存在
		if (null == pMenu) {
			map.setMsg("父节点不存在");
			return map;
		}

		if (null == entity.getId()) {
			entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}

		if (null == entity.getSort()) {
			entity.setSort(1);
		}

		if (null == entity.getIs_parent()) {
			entity.setIs_parent(0);
		}

		entity.setPath(("null".equals(pMenu.getPath())) ? pMenu.getPid()
				: (pMenu.getPath() + "," + pMenu.getPid()));

		save(entity);

		map.setData(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> editInfo(Menu entity) {
		ResultMap<Void> map = new ResultMap<Void>();
		updateNotNull(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> remove(String id) {
		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		List<Menu> list = findByPid(id);

		if (0 < list.size()) {
			map.setMsg("请先删除子节点");
			return map;
		}

		delete(id);

		map.setSuccess(true);
		return map;
	}

}
