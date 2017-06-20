package net.foreworld.model;

import java.io.Serializable;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 76843215655192635L;

	private String id;

	private String sender;
	private String receiver;
	private String comment;
	private Long create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

}
