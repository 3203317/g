package net.foreworld.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import net.foreworld.model.Message;
import net.foreworld.service.MessageService;

/**
 *
 * @author Administrator
 *
 */
@Service("messageService")
public class MessageServiceImpl extends BaseService<Message> implements
		MessageService {

	@Override
	public int save(Message entity) {
		entity.setCreate_time(new Date());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(Message entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Override
	public List<Message> findByMessage(Message message, int page, int rows) {
		Example example = new Example(Message.class);
		example.setOrderByClause("create_time desc");

		PageHelper.startPage(page, rows);
		return selectByExample(example);
	}

}