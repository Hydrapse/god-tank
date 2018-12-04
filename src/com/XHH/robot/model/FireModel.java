package com.XHH.robot.model;

import com.XHH.robot.GodTank;
import com.XHH.robot.entity.Enemy;

import robocode.util.Utils;

public final class FireModel {
	private static GodTank robot; // 运行此模块的机器人

	/**
	 * 初始化
	 */
	public static void init() {
		robot = GodTank.getInstance();
	}

	/**
	 * 工具类不可能有实例
	 */
	private FireModel() {
		throw new IllegalArgumentException("FireModel工具类不可能有实例！");
	}

	/**
	 * 进行一次的开火操作
	 */
	public static void doFireCycle() {
		if (!EnemyModel.existEnemy())
			return;
		Enemy target = EnemyModel.getNowTarget();
		double power = Math.min(3, 400 / target.getDistance());
		double rVelocity = target.getVelocity();
		double bVelocity = 20 - 3 * power;
		double direction = robot.getHeadingRadians() + target.getBearing();
		double maxAngle = Math.asin(rVelocity / bVelocity);
		double fireOffset = Math.random() * maxAngle;
		if (Utils.normalRelativeAngle(target.getHeading() - direction) < 0) {
			fireOffset *= -1;
		}
		double gunOffset = Utils.normalRelativeAngle(direction - robot.getGunHeadingRadians() + fireOffset);
		robot.setTurnGunRightRadians(gunOffset);
		robot.setFire(power);
	}
}
