package com.XHH.rebot;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * 本类储存机器人的当前目标的一些数据。
 * 我们不会在切换攻击目标的时候新建一个本类实例，因此采用单例模式。
 * @author 郭江龙，邓港大
 */
public class MyEnemy {
	private static boolean isInit = false;    //是否已经初始化
	private static MyEnemy enemy;    //本类的单例对象
	
	/**
	 * 初始化，启动单例模式
	 */
	public static void init() {
		 enemy = new MyEnemy();
		 isInit = true;
	}
	
	/**
	 * 获取是否已经初始化
	 * @return 是否已经初始化
	 */
	public static boolean isInit() {
		return isInit;
	}
	
	/**
	 * 获取单例模式中本类实例
	 * @return 本类的实例
	 */
	public static MyEnemy getInstance() {
		if(!isInit) {
			throw new IllegalArgumentException("MyEnemy还没有初始化！");
		}
		return enemy;
	}
	
	private boolean enable = false;
	private double heading;
	private double bearing;
	private double velocity;
	private double distance = 10000;
	private double direction;
	private String name;
	
	//getter and settter
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

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

	/**
	 * 单例模式只允许自身调用一次构造方法，因此采用private标识符。
	 * 但是通过反射，我们依然能够得到构造方法的调用。
	 * 所以这里抛出异常，保证非法操作不可能被调用。
	 */
	private MyEnemy() {
		if(isInit) {
			throw new IllegalArgumentException("MyEnemy已经初始化了！");
		}
	}
	
	public void update(ScannedRobotEvent e, AdvancedRobot me) {
		enable = true;
		heading = e.getHeadingRadians();
		bearing = e.getBearingRadians();
		velocity = e.getVelocity();
		distance = e.getDistance();
		name = e.getName();
		direction = me.getHeadingRadians() + e.getBearingRadians();
	}
}
