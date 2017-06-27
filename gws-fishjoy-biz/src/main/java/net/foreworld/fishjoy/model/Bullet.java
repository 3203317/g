package net.foreworld.fishjoy.model;

import java.io.Serializable;

/**
 *
 * @author huangxin
 *
 */
public class Bullet implements Serializable {

	private static final long serialVersionUID = -3978562827886194600L;

	private String id;

	private Integer level;
	private String sender;
	private Float x;
	private Float y;
	private Integer speed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

}
