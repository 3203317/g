package net.foreworld.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Table(name = "s_menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1520679202112364600L;

	@Id
	@Column(name = "id")
	private String id;

	private String menu_name;
	private String menu_url;
	private String path;
	private String pid;

	private Integer sort;
	private Integer is_parent;

	/*-----分割线-----*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIs_parent() {
		return is_parent;
	}

	public void setIs_parent(Integer is_parent) {
		this.is_parent = is_parent;
	}

}
