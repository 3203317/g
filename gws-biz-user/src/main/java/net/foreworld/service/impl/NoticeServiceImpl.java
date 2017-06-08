package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.foreworld.model.Notice;
import net.foreworld.service.NoticeService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author huangxin
 *
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseService<Notice> implements NoticeService {

	@Override
	public int save(Notice entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(Notice entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<Notice> findByNotice(Notice entity, int page, int rows) {
		Example example = new Example(Notice.class);
		example.setOrderByClause("create_time desc");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

}
