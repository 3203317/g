package net.foreworld.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 *
 * @author Administrator
 *
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
