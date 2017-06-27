package net.foreworld.fishjoy.model;

import java.io.Serializable;

/**
 *
 * @author huangxin
 *
 */
public class Fish implements Serializable {

	private static final long serialVersionUID = -6594210220189780333L;

	private String id;

	private Integer fish_type;
	private Integer score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getFish_type() {
		return fish_type;
	}

	public void setFish_type(Integer fish_type) {
		this.fish_type = fish_type;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
