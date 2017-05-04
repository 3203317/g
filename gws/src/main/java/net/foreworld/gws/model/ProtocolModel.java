package net.foreworld.gws.model;

import java.io.Serializable;

/**
 *
 * @author huangxin
 *
 */
public class ProtocolModel implements Serializable {

	private static final long serialVersionUID = -249876415170022141L;

	private int version;
	private int method;
	private int seqId;
	private Long timestamp;
	private String data;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
