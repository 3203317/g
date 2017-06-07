package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author huangxin
 *
 */
@Table(name = "s_user_friends")
public class UserFriends implements Serializable {

	private static final long serialVersionUID = 4057569727658747571L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/**
	 * 申请人
	 */
	private String friend_a;
	/**
	 * 被申请人
	 */
	private String friend_b;

	private String friend_a_alias;
	private String friend_b_alias;

	private Date create_time;
	private Integer status;

	private String apply_content;

	/**
	 * 拒绝原因
	 */
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApply_content() {
		return apply_content;
	}

	public void setApply_content(String apply_content) {
		this.apply_content = apply_content;
	}

	public String getFriend_a() {
		return friend_a;
	}

	public void setFriend_a(String friend_a) {
		this.friend_a = friend_a;
	}

	public String getFriend_b() {
		return friend_b;
	}

	public void setFriend_b(String friend_b) {
		this.friend_b = friend_b;
	}

	public String getFriend_a_alias() {
		return friend_a_alias;
	}

	public void setFriend_a_alias(String friend_a_alias) {
		this.friend_a_alias = friend_a_alias;
	}

	public String getFriend_b_alias() {
		return friend_b_alias;
	}

	public void setFriend_b_alias(String friend_b_alias) {
		this.friend_b_alias = friend_b_alias;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
