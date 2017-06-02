package net.foreworld.gws.model;

import java.io.Serializable;

/**
 *
 * @author huangxin
 *
 */
public class ProtocolModel implements Serializable {

	private static final long serialVersionUID = -7476803059012167127L;

	private Integer version;
	private Integer method;
	private Long seqId;
	private Long timestamp;
	private String data;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
