package net.foreworld.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 *
 * @param <T>
 */
@Service
public interface IService<T> {

	T selectByKey(Object key);

	T getById(Object key);

	int save(T entity);

	int delete(Object key);

	int updateAll(T entity);

	int updateNotNull(T entity);

	List<T> selectByExample(Object example);

	@Transactional
	int deleteByKeys(String keys);
}
