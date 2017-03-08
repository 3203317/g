package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.Notice;
import net.foreworld.model.ResultMap;
import net.foreworld.service.NoticeService;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseService<Notice> implements
		NoticeService {

	@Override
	public int save(Notice entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		entity.setView_count(0);
		return super.save(entity);
	}

	@Override
	public int updateNotNull(Notice entity) {
		entity.setCreate_time(null);
		entity.setUser_id(null);
		return super.updateNotNull(entity);
	}

	@Override
	public Notice viewById(String id) {
		Notice entity = getById(id);

		if (null == entity)
			return null;

		entity.setView_count(entity.getView_count() + 1);
		updateView_count(id, entity.getView_count());

		return entity;
	}

	/**
	 * 修改浏览量
	 *
	 * @param id
	 * @param view_count
	 */
	private void updateView_count(String id, int view_count) {
		Notice entity = new Notice();
		entity.setId(id);
		entity.setView_count(view_count);
		updateNotNull(entity);
	}

	@Override
	public List<Notice> findByNotice(Notice entity, int page, int rows) {
		Example example = new Example(Notice.class);
		example.setOrderByClause("create_time desc");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

	@Override
	public ResultMap<Void> editInfo(Notice entity) {
		ResultMap<Void> map = new ResultMap<Void>();
		updateNotNull(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Notice> saveNew(Notice entity) {
		ResultMap<Notice> map = new ResultMap<Notice>();
		save(entity);
		map.setData(entity);
		map.setSuccess(true);
		return map;
	}

	@Override
	public ResultMap<Void> remove(String id) {
		ResultMap<Void> map = new ResultMap<Void>();
		delete(id);
		map.setSuccess(true);
		return map;
	}

}
