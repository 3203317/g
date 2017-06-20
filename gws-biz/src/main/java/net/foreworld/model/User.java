package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -6390664045260044818L;

	private String id;

	private String group_name;
	private Date create_time;

	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
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
