package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 *
 */
@Table(name = "s_cfg")
public class SysCfg implements Serializable {

	private static final long serialVersionUID = 4405049762353813061L;

	@Id
	@Column(name = "key_")
	private String key_;

	private String value_;
	private String title;
	private Date create_time;
	private String comment;

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}

	public String getValue_() {
		return value_;
	}

	public void setValue_(String value_) {
		this.value_ = value_;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
