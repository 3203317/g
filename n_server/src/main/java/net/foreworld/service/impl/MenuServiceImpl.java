package net.foreworld.service.impl;

import java.util.List;
import java.util.UUID;

import net.foreworld.model.Menu;
import net.foreworld.model.ResultMap;
import net.foreworld.service.MenuService;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author Administrator
 *
 */
@Service("menuService")
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	@Override
	public List<Menu> findByMenu(Menu menu, int page, int rows) {
		Example example = new Example(Menu.class);
		example.setOrderByClause("sort ASC");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public List<Menu> findByPid(String pid) {

		Example example = new Example(Menu.class);
		example.setOrderByClause("sort ASC");

		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pid", pid);

		return selectByExample(example);
	}

	@Override
	public ResultMap<Menu> createMenu(Menu menu) {

		ResultMap<Menu> map = new ResultMap<Menu>();
		map.setSuccess(false);

		// 获取父节点
		Menu pMenu = selectByKey(menu.getPid());

		// 判断父节点是否存在
		if (null == pMenu) {
			map.setMsg("父节点不存在");
			return map;
		}

		if (null == menu.getId()) {
			menu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}

		if (null == menu.getSort()) {
			menu.setSort(1);
		}

		if (null == menu.getIs_parent()) {
			menu.setIs_parent(0);
		}

		save(menu);

		map.setData(menu);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> editInfo(Menu menu) {

		ResultMap<Void> map = new ResultMap<Void>();

		menu.setPid(null);
		updateNotNull(menu);

		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> remove(String menu_id) {

		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		List<Menu> list = findByPid(menu_id);

		if (0 < list.size()) {
			map.setMsg("请先删除子节点");
			return map;
		}

		deleteByKeys(menu_id);

		map.setSuccess(true);
		return map;
	}

}
