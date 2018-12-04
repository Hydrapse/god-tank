package com.XHH.robot.engine;

import com.XHH.robot.GodTank;
import com.XHH.robot.model.FireModel;
import com.XHH.robot.model.RunModel;
import com.XHH.robot.model.ScanModel;

/**
 * 表示机器人的主循环，是一个工具类
 * 
 * @author 郭江龙
 *
 */
public final class MainEngine {

	private static GodTank robot;    //运行此模块的机器人

	public static void init() {
		robot = GodTank.getInstance();
	}

	/**
	 * 主循环
	 */
	public static void mainCycle() {
		while (true) {
			long startTime = System.currentTimeMillis();
			ScanModel.doScanCycle();
			FireModel.doFireCycle();
			if (robot.getTurnRemaining() != 0 && robot.getDistanceRemaining() != 0) {
				robot.execute();
				continue;
			}
			RunModel.doRunCycle();
			robot.execute();
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime) + "ms");
		}
	}

	/**
	 * 工具类不可能有实例
	 */
	private MainEngine() {
		throw new IllegalArgumentException("MainEngine工具类不可能有实例！");
	}
}
