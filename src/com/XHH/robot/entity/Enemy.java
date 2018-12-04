package com.XHH.robot.entity;

import com.XHH.robot.GodTank;

import robocode.ScannedRobotEvent;

/**
 * 一个敌人的实例，一般用来储存当前目标敌人
 * 
 * @author 郭江龙
 *
 */
public class Enemy {
	private double heading; // 敌人面对的方向
	private double bearing; // 敌人对于你的方位
	private double velocity; // 敌人的瞬时速率
	private double distance; // 敌人与你之间的距离
	private double direction; // 你面对的方位+敌人相对你的方位
	private String name;

	public Enemy(double heading, double bearing, double velocity, double distance, String name) {
		super();
		this.heading = heading;
		this.bearing = bearing;
		this.velocity = velocity;
		this.distance = distance;
		this.direction = GodTank.getInstance().getHeadingRadians() + bearing;
		this.name = name;
	}

	public Enemy(ScannedRobotEvent e) {
		super();
		heading = e.getHeadingRadians();
		bearing = e.getBearingRadians();
		velocity = e.getVelocity();
		distance = e.getDistance();
		direction = GodTank.getInstance().getHeadingRadians() + bearing;
		name = e.getName();
	}

	// getter and settter
	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
