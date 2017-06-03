package net.foreworld.model;

/**
 *
 * @author Administrator
 *
 * @param <T>
 */
public class ResultMap<T> {

	private T data;
	private Boolean success;
	private String msg;
	private String code;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
