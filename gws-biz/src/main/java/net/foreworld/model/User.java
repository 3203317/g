package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -8840009604804059617L;

	private String id;

	private String user_name;
	private String nickname;
	private Date create_time;

	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
