package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Menu;
import net.foreworld.model.ResultMap;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface MenuService extends IService<Menu> {

	/**
	 *
	 * @param entity
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Menu> findByMenu(Menu entity, int page, int rows);

	/**
	 *
	 * @param pid
	 * @return
	 */
	List<Menu> findByPid(String pid);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<Menu> saveNew(Menu entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	ResultMap<Void> editInfo(Menu entity);

	/**
	 *
	 * @param id
	 * @return
	 */
	ResultMap<Void> remove(String id);

}
