package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Menu;
import net.foreworld.model.ResultMap;

/**
 *
 * @author Administrator
 *
 */
public interface MenuService extends IService<Menu> {

	List<Menu> findByMenu(Menu menu, int page, int rows);

	List<Menu> findByPid(String pid);

	/**
	 * 判断父节点是否存在
	 *
	 * @param menu
	 * @return
	 */
	ResultMap<Menu> createMenu(Menu menu);

	ResultMap<Void> editInfo(Menu menu);

	/**
	 * 判断是否还有子节点
	 *
	 * @param menu_id
	 *            主键
	 * @return
	 */
	ResultMap<Void> remove(String menu_id);

}
