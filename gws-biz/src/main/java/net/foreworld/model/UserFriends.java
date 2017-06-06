package net.foreworld.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Table(name = "s_user_friends")
public class UserFriends implements Serializable {

	private static final long serialVersionUID = 3949162263630438013L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
