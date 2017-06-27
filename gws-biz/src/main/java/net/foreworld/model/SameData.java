package net.foreworld.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class SameData<T> implements Serializable {

	private static final long serialVersionUID = -3712160275068904807L;

	private List<Receiver<Void>> receivers;

	private T data;

	public List<Receiver<Void>> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<Receiver<Void>> receivers) {
		this.receivers = receivers;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
