package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 网站公告
 *
 * @author Administrator
 *
 */
@Table(name = "w_notice")
public class Notice implements Serializable {

	private static final long serialVersionUID = 4204799309802253673L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	private String title;

	private String content;

	private Date create_time;

	private String user_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}