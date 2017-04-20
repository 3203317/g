package net.foreworld.gws.model;

import java.io.Serializable;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class Msg implements Serializable {

	private static final long serialVersionUID = 2285446295793394084L;

	private int packetLen;

	private short version;

	private short method;

	private long token;

	private int seqId;

	private byte[] body;

	public int getPacketLen() {
		return packetLen;
	}

	public void setPacketLen(int packetLen) {
		this.packetLen = packetLen;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public short getMethod() {
		return method;
	}

	public void setMethod(short method) {
		this.method = method;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
