package com.XHH.rebot;

import java.awt.Color;
import robocode.*;
import robocode.util.*;
public class Yuanxinji extends AdvancedRobot{
	Enemy1 target;
	
	public void run(){
		double eDist = 0 ; //对方的距离
		double move = 0; //移动的距离
		double radarMove = 25; //雷达移动的角度
		eDist = 300;
		init();
		while(true){
			this.setMaxVelocity(5);
			//每过一个周期，运动随机的距离
			double period = 6*((int)(eDist/20)); //周期；敌人越接近，周期越短，移动越频繁
			//周期开始，则移动
			if(getTime()%period == 1){
				move = (Math.random()*2-1)*(period*8 - 25);
				setTurnLeft(Math.random()*2-1 * eDist);
				setAhead(move + ((move >= 0) ? 25: -25) + 50);
				System.out.println(getBattleFieldWidth());
			}
			else setAhead(move + ((move >= 0) ? 25: -25) + 50);
			//避免撞墙
			double heading = getHeadingRadians(); //取得bot方向的弧度数
			double x = getX() + move*Math.sin(heading); //移动move后将要达到的x坐标
			double y = getY() + move*Math.cos(heading); //移动move后将要达到的y坐标
			double dWidth = getBattleFieldWidth(); //战场的宽度
			double dHeight = getBattleFieldHeight(); //战场的长度
			//当(x,y)超过指定的范围，则反向移动move
			if(x < 25 || x > dWidth-25 || y < 25 || y > dHeight-25){
				setBack(move);
			}
			
			else if( (getX() < 520 && getX()> 260 ) ||(getY() < 400 && getY() > 200))
			{
				setAhead(200);
			}

			//turnRadarLeft(radarMove); 
			
			setTurnRight(2*Math.PI);
			doScan();
			doRamFire();
			setAhead(200);
			execute();
			//turnRadarRight(Double.POSITIVE_INFINITY);
		}
	}
public void onHitWall(HitWallEvent e) {
		
		double X=getX();
		double Y=getY();
		System.out.println(X+" "+Y);
		if(Y<=30) {
			if(X<=400) {
				this.turnLeft(getHeading()-45);
			}
			else {
				this.turnRight(315-getHeading());
			}
		}
		else if(Y>=getBattleFieldHeight()-30) {
			if(X<=400) {
				this.turnLeft(getHeading()-135);
			
			}
			else {
				this.turnRight(225-getHeading());
			}
		}
		else if(X<=30) {
			if(Y<=400) {
				this.turnLeft(getHeading()-45);
			}
			else {
				this.turnLeft(getHeading()-135);
			}
			
		}
		else if(X>=getBattleFieldWidth()-30) {
			if(Y<=400) {
				this.turnLeft(getHeading()-315);
			}
			else {
				this.turnLeft(getHeading()-225);
			}
		}
		this.setMaxVelocity(8);
		ahead(200);
		this.setMaxVelocity(5);
	}

	
	public void onScannedRobot(ScannedRobotEvent e){
		if(e.getName().equals(target.name) || (e.getDistance() < target.distance)){
			
			target.update(e, this);
			//doScan();
			//doRamFire();
		}
		
	}
	 /*public void onHitByBullet(HitByBulletEvent event) {
		 this.setMaxVelocity(7);
		 this.turnLeft(10);
		 back(50);
		 this.setMaxVelocity(4);
	   }*/
	public void onHitRobot(HitRobotEvent e){
		//if((e.getBearingRadians() > -Math.PI/18)
				//&& (e.getBearingRadians() < Math.PI/18));
		double lOffset = Utils.normalRelativeAngle(
				e.getBearingRadians() + getGunHeadingRadians()
				- Math.PI/18);
		double rOffset = Utils.normalRelativeAngle(
				e.getBearingRadians() + getGunHeadingRadians()
				- Math.PI/18);
		double gunOffset = (Math.abs(lOffset) < Math.abs(rOffset)) 
				? lOffset : rOffset;
		this.setMaxVelocity(8);
	
		setTurnGunRightRadians(gunOffset);
		setFire(3);
		setTurnRightRadians(Utils.normalRelativeAngle(
				e.getBearingRadians() + Math.PI));
		
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
	        back(400);
	       }
		else {
	        ahead(400);
	       }
		this.setMaxVelocity(5);
	}
	
	
	public void onRobotDeath(RobotDeathEvent e){
		target.distance = 1000;
	}
	
	public void init(){
		target = new Enemy1();
		setBodyColor(Color.BLACK);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLACK);
		setScanColor(Color.cyan);
		setBulletColor(Color.white);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		turnRadarRightRadians(2*Math.PI);
	}
	
	public void doRamFire(){
		double power = Math.min(3, 400/target.distance);
		double rVelocity = target.velocity;
		double bVelocity = 20 - 3*power;
		double direction = getHeadingRadians() + target.bearing;
		
		double maxAngle = Math.asin(rVelocity/bVelocity);
		
		double fireOffset = Math.random()*maxAngle;
	    if(Utils.normalRelativeAngle(
				target.heading - direction) < 0)
			fireOffset *= -1;
		double gunOffset = Utils.normalRelativeAngle(
				direction - getGunHeadingRadians() + fireOffset);
		
		setTurnGunRightRadians(gunOffset);
		setFire(power);
	}
	
	public void doScan(){
		double radarOffset = Utils.normalRelativeAngle(
				getHeadingRadians() + target.bearing
				- getRadarHeadingRadians());
		setTurnRadarRight(Math.toDegrees(radarOffset)*1.1);
	}
}

class Enemy1{
	double heading;
	double bearing;
	double velocity;
	double distance = 10000;
	double direction;
	String name;
	
	
	public void update(ScannedRobotEvent e, AdvancedRobot me){
		heading = e.getHeadingRadians();
		bearing = e.getBearingRadians();
		velocity = e.getVelocity();
		distance = e.getDistance();
		name = e.getName();
		direction = me.getHeadingRadians() + e.getBearingRadians();
	}
}
