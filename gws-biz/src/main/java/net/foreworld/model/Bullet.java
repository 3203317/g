package net.foreworld.model;

import java.io.Serializable;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class Bullet implements Serializable {

	private static final long serialVersionUID = -439989810486257425L;

	private Integer level;

	private Float angle;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Float getAngle() {
		return angle;
	}

	public void setAngle(Float angle) {
		this.angle = angle;
	}

}
