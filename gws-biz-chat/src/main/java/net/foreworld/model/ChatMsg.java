package net.foreworld.model;

import java.io.Serializable;

/**
 * 
 * @author huangxin
 *
 */
public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 8147791864211943484L;

	private String id;

	private String sender;
	private String receiver;
	private String message;
	private String extend_data;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExtend_data() {
		return extend_data;
	}

	public void setExtend_data(String extend_data) {
		this.extend_data = extend_data;
	}

}
