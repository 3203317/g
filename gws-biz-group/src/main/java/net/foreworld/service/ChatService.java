package net.foreworld.service;

import java.util.List;

import net.foreworld.model.Chat;

/**
 * 
 * @author huangxin
 *
 */
public interface ChatService extends IService<Chat> {

	enum Status {
		READ(1), UNREAD(0);

		private int value = 0;

		private Status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	List<Chat> findByChat(Chat entity, int page, int rows);

}