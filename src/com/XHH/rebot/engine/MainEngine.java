package com.XHH.rebot.engine;

import com.XHH.rebot.GodTank;

/**
 * 表示机器人的主循环，是一个工具类
 * @author 郭江龙
 *
 */
public final class MainEngine {
	
	/**
	 * 主循环
	 * @param robot 调用这个主循环的机器人
	 */
	public static void mainCycle(GodTank robot) {
		while(true) {
			long startTime = System.currentTimeMillis();
			double x = robot.getX(), y = robot.getY(), degree = robot.getHeading();
		}
	}
	
	/**
	 * 工具类不可能有实例
	 */
	private MainEngine(){
		throw new IllegalArgumentException("工具类不可能有实例！");
	}
}
