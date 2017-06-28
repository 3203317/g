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

	private Integer equip;
	private Integer equip_count;

	public Integer getEquip() {
		return equip;
	}

	public void setEquip(Integer equip) {
		this.equip = equip;
	}

	public Integer getEquip_count() {
		return equip_count;
	}

	public void setEquip_count(Integer equip_count) {
		this.equip_count = equip_count;
	}

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
