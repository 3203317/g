package net.foreworld.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Administrator
 *
 */
@Table(name = "s_user")
public class User implements Serializable {

	private static final long serialVersionUID = 3660088808064034358L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/**
	 * 推荐人
	 */
	private String pid;

	private String user_name;

	private String user_pass;

	private String real_name;

	private String mobile;

	private String nickname;

	private String email;

	private String alipay_account;

	private String wx_account;

	private Date create_time;

	/**
	 * 0禁用
	 *
	 * 1正常
	 *
	 * 2删除
	 */
	private Integer status;

	private String apikey;
	private String seckey;

	@Transient
	private User _t_pUser;

	public User get_t_pUser() {
		return _t_pUser;
	}

	public void set_t_pUser(User _t_pUser) {
		this._t_pUser = _t_pUser;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public String getWx_account() {
		return wx_account;
	}

	public void setWx_account(String wx_account) {
		this.wx_account = wx_account;
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

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getSeckey() {
		return seckey;
	}

	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}
}
