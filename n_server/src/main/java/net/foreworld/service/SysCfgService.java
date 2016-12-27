package net.foreworld.service;

import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.model.SysCfg;

/**
 *
 * @author Administrator
 *
 */
public interface SysCfgService extends IService<SysCfg> {

	List<SysCfg> findBySysCfg(SysCfg sysCfg, int page, int rows);

	ResultMap<Void> editInfo(SysCfg sysCfg);

}
