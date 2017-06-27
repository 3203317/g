package net.foreworld.fishjoy.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author huangxin
 *
 */
public class BulletBlast implements Serializable {

	private static final long serialVersionUID = -6553266602150982465L;

	Bullet bullet;

	private Float x;
	private Float y;

	private List<FishDead> fishDeads;

	public List<FishDead> getFishDeads() {
		return fishDeads;
	}

	public void setFishDeads(List<FishDead> fishDeads) {
		this.fishDeads = fishDeads;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

}
