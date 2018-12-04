package com.XHH.robot;

import java.awt.Color;

import com.XHH.robot.engine.MainEngine;
import com.XHH.robot.entity.Enemy;
import com.XHH.robot.model.EnemyModel;
import com.XHH.robot.model.FireModel;
import com.XHH.robot.model.RunModel;
import com.XHH.robot.model.ScanModel;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
/**问题1：碾死人不刷新
 * 问题2：被别人抢人头不刷新
 */

/**
 * 无可抵挡的机器人，在每一局游戏里都是单例模式。
 * 
 * @author 郭江龙，邓港大，黎梓晟，李振豪，刘展坤
 */
public final class GodTank extends AdvancedRobot {
	private static GodTank instance; // 当前状态的本类实例

	/**
	 * 获取本类单例
	 * 
	 * @return 实例
	 */
	public static GodTank getInstance() {
		return instance;
	}

	/**
	 * 当对象被创建的时候，就应当执行的初始化操作
	 */
	public GodTank() {
		GodTank.instance = this;
	}

	/**
	 * 一些初始化操作，Robocode要求在{@link GodTank#run()}函数中引用
	 */
	private void init() {
		setColors(Color.BLUE, Color.GREEN, Color.RED);
		setMaxVelocity(Double.MAX_VALUE);
		setMaxTurnRate(Double.MAX_VALUE);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		MainEngine.init();
		RunModel.init();
		ScanModel.init();
		FireModel.init();
		EnemyModel.init();
	}

	/**
	 * 由于每个机器人是一个线程类， 因此应当覆写其run()方法定义线程入口。
	 */
	@Override
	public void run() {
		init();
		while (true) {
			MainEngine.mainCycle();
		}
	}

	/**
	 * 扫描到敌方机器人的事件实现
	 * 
	 * @param e 扫描到机器人事件
	 */
	@Override
	

	public void onScannedRobot(ScannedRobotEvent e) {
		if (EnemyModel.existEnemy()) {
			Enemy target = EnemyModel.getNowTarget();
			if (e.getName().equals(target.getName()) || (e.getDistance() < target.getDistance())) {
				EnemyModel.update(new Enemy(e));
			}
		} else {
			EnemyModel.update(new Enemy(e));
		}
	}

	/**
	 * 碰撞到敌方机器人的事件实现
	 * 
	 * @param e 碰撞到敌方机器人事件
	 */
	@Override
	public void onHitRobot(HitRobotEvent e) {
		EnemyModel.update(new Enemy(e));
		setFire(3);
		if(getDistanceRemaining() >= 0) {
			setBack(1000);
		}else {
			setAhead(1000);
		}
		execute();
	}

	/**
	 * normalRelativeAngle: Returns angle such that -180 < angle <= 180
	 */
	public double normalRelativeAngle(double angle) {
		if (angle > -180 && angle <= 180) {
			return angle;
		}
		double fixedAngle = angle;

		while (fixedAngle <= -180) {
			fixedAngle += 360;
		}
		while (fixedAngle > 180) {
			fixedAngle -= 360;
		}
		return fixedAngle;
	}

	/**
	 * 我方机器人死亡的事件实现
	 * <p>
	 * 关闭MyEnemy准备下一回合的游戏
	 * 
	 * @param e 我方机器人死亡事件
	 */
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		EnemyModel.clear();
	}

	/**
	 * 打中敌方机器人的事件实现
	 * <p>
	 * 这里用来判断是否打死，若打死则把MyEnemy关闭
	 * 
	 * @param e 打中敌方机器人事件
	 */
	@Override
	public void onBulletHit(BulletHitEvent e) {
		if (e.getEnergy() <= 0) {
			EnemyModel.clear();
		}
	}
}
