package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Notice;

/**
 *
 * @author Administrator
 *
 */
public interface NoticeService extends IService<Notice> {

	List<Notice> findByNotice(Notice notice, int page, int rows);
}
