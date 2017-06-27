package net.foreworld.fishjoy.model;

import java.io.Serializable;

/**
 *
 * @author huangxin
 *
 */
public class FishDead implements Serializable {

	private static final long serialVersionUID = -7719487001358188342L;

	private Fish fish;
	private Integer score;

	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
