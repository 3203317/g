package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Message;

/**
 *
 * @author Administrator
 *
 */
public interface MessageService extends IService<Message> {

	List<Message> findByMessage(Message message, int page, int rows);
}
