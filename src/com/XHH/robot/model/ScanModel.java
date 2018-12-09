package com.XHH.robot.model;

import java.lang.Math;

import com.XHH.robot.GodTank;
import com.XHH.robot.entity.Enemy;

import robocode.util.Utils;

/**
 * 遵从OOP原则，采用分类模块化编程。
 * <p>
 * 扫描模块，工具类。
 * 
 * @author 郭江龙
 *
 */
public final class ScanModel {
	private static boolean isScanned = false;
	private static GodTank robot; // 运行此模块的机器人

	// getter and setter
	public static boolean isScanned() {
		return isScanned;
	}

	public static void setScanned(boolean isScanned) {
		ScanModel.isScanned = isScanned;
	}

	/**
	 * 初始化
	 */
	public static void init() {
		robot = GodTank.getInstance();
	}

	/**
	 * 进行一次扫描操作
	 */
	public static void doScanCycle() {
		if (robot.getRadarTurnRemaining() == 0) {
			robot.setTurnRadarLeft(360);
		}
		if (!isScanned) {
			EnemyModel.clear();
			return;
		}
		if (!EnemyModel.existEnemy())
			return;
		Enemy target = EnemyModel.getNowTarget();
		double radarOffset = Utils
				.normalRelativeAngle(robot.getHeadingRadians() + target.getBearing() - robot.getRadarHeadingRadians());
		robot.setTurnRadarRight(Math.toDegrees(radarOffset) * 1.1);
		isScanned = false;
	}

	/*
	 * 工具类不可能有实例
	 */
	private ScanModel() {
		throw new IllegalArgumentException("ScanModel工具类不可能有实例！");
	}
}
