package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface SysCfgService extends IService<SysCfg> {

	List<SysCfg> findBySysCfg(SysCfg entity, int page, int rows);

	ResultMap<Void> editInfo(SysCfg entity);

}
