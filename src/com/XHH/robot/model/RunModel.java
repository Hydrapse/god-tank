package com.XHH.robot.model;

import java.lang.reflect.Method;

import com.XHH.robot.GodTank;
import com.XHH.robot.enums.RobotLocation;

/**
 * 遵从OOP原则，采用分类模块化编程。
 * <p>
 * 运动模块，工具类。
 * 
 * @author 郭江龙
 *
 */
public final class RunModel {
	private static final int edge = 200; // 自定义的边界长度

	private static boolean flag = true; // 蛇皮走位，左转还是右转
	private static GodTank robot; // 定义robot的引用，方便调用
	private static Class<?> cls; // 反射到的类

	/**
	 * 初始化RunModel工具类 使用反射配合接下来的反射操作
	 */
	public static void init() {
		robot = GodTank.getInstance();
		try {
			cls = Class.forName("com.XHH.robot.avoid.AvoidWallSystem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进行一次运动的操作
	 */
	public static void doRunCycle() {
		double x = robot.getX(), y = robot.getY(), degree = robot.getHeading();
		if (!RunModel.avoidHitWall(RunModel.calcWhereRobotBeIn(x, y), degree)) {
			if (flag) {
				robot.setTurnLeft(270);
			} else {
				robot.setTurnRight(270);
			}
			flag = !flag;
			robot.setAhead(100);
		}
	}

	/**
	 * 启用防撞墙系统。
	 * <p>
	 * 这里使用了反射，大量减少冗余代码。
	 * 
	 * @param loc    机器人现在处于地图中的位置
	 * @param degree 机器人车
	 */
	public static boolean avoidHitWall(RobotLocation loc, double degree) {
		int headLoc = (int) degree / 90;
		if (headLoc == 5) {
			headLoc = 0;
		}
		++headLoc;
		try {
			System.out.println("avoid" + headLoc);
			Method avoid = cls.getMethod("avoid" + headLoc, RobotLocation.class);
			return (Boolean) avoid.invoke(null, loc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 计算机器人所处的位置。
	 * 
	 * @param x 机器人x坐标
	 * @param y 机器人y坐标
	 * @return 枚举表示机器人所属方位
	 */
	public static RobotLocation calcWhereRobotBeIn(double x, double y) {
		double top = robot.getBattleFieldHeight() - edge;
		double down = edge;
		double left = edge;
		double right = robot.getBattleFieldWidth() - edge;
		if (x < left && y < down) {
			return RobotLocation.DOWN_LEFT;
		} else if (x > right && y > top) {
			return RobotLocation.TOP_RIGHT;
		} else if (x < left && y > top) {
			return RobotLocation.TOP_LEFT;
		} else if (x > right && y < down) {
			return RobotLocation.DOWN_RIGHT;
		} else if (x < left) {
			return RobotLocation.LEFT;
		} else if (y < down) {
			return RobotLocation.DOWN;
		} else if (x > right) {
			return RobotLocation.TOP_RIGHT;
		} else if (y > top) {
			return RobotLocation.TOP;
		} else {
			return RobotLocation.CENTER;
		}
	}

	/**
	 * 工具类不可能有实例
	 */
	private RunModel() {
		throw new IllegalArgumentException("RunModel工具类不可能有实例！");
	}
}
