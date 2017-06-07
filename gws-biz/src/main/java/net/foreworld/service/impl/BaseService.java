package net.foreworld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.foreworld.service.IService;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 * @author Administrator
 *
 * @param <T>
 */
public abstract class BaseService<T> implements IService<T> {

	@Autowired
	protected Mapper<T> mapper;

	public Mapper<T> getMapper() {
		return mapper;
	}

	@Override
	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	public T getById(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	public int save(T entity) {
		return mapper.insert(entity);
	}

	public int delete(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	public int updateAll(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	public int updateNotNull(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}

	public int deleteByKeys(String keys) {
		String[] _keys = keys.split(",");
		int result = 0;

		for (String key : _keys) {
			result += delete(key);
		}
		return result;
	}
}
