package com.XHH.robot.model;

import com.XHH.robot.GodTank;
import com.XHH.robot.enums.RobotLocation;

/**
 * 防撞墙系统，工具类
 * <p>
 * 对于不同角度有不同的防撞墙判断，每一个都是这个的一个静态方法
 * 
 * @author 郭江龙
 *
 */
public final class AvoidWallSystem {

	/**
	 * 工具类不可能有实例
	 */
	private AvoidWallSystem() {
		throw new IllegalArgumentException("AvoidWallSystem工具类不可能有实例！");
	}

	/**
	 * 当机器人的机身方向面对的是0~90度时，处于地图的loc的位置，应当怎么躲避撞墙
	 * 
	 * @param loc 机器人处于地图中的位置
	 * @return 是否可能会撞墙
	 */
	public static boolean avoid1(RobotLocation loc) {
		GodTank robot = GodTank.getInstance();
		switch (loc) {
		case CENTER:
		case DOWN_LEFT:
		case DOWN:
		case LEFT:
			return false;
		case TOP_LEFT:
			robot.setTurnRight(Math.PI / 2);
			robot.setAhead(100);
			return true;
		case TOP:
			robot.setTurnRight(Math.PI / 2);
			robot.setBack(100);
			return true;
		case TOP_RIGHT:
		case RIGHT:
		case DOWN_RIGHT:
			robot.setTurnLeft(Math.PI / 2);
			robot.setBack(100);
			return true;
		}
		return false;
	}

	/**
	 * 当机器人的机身方向面对的是90~180度时，处于地图的loc的位置，应当怎么躲避撞墙
	 * 
	 * @param loc 机器人处于地图中的位置
	 * @return 是否可能会撞墙
	 */
	public static boolean avoid2(RobotLocation loc) {
		GodTank robot = GodTank.getInstance();
		switch (loc) {
		case CENTER:
		case TOP_LEFT:
		case TOP:
		case LEFT:
			return false;
		case DOWN_LEFT:
		case DOWN:
			robot.setTurnRight(Math.PI / 2);
			robot.setBack(100);
			return true;
		case TOP_RIGHT:
		case RIGHT:
		case DOWN_RIGHT:
			robot.setTurnLeft(Math.PI / 2);
			robot.setBack(100);
			return true;
		}
		return false;
	}

	/**
	 * 当机器人的机身方向面对的是180~270度时，处于地图的loc的位置，应当怎么躲避撞墙
	 * 
	 * @param loc 机器人处于地图中的位置
	 * @return 是否可能会撞墙
	 */
	public static boolean avoid3(RobotLocation loc) {
		GodTank robot = GodTank.getInstance();
		switch (loc) {
		case CENTER:
		case TOP_RIGHT:
		case TOP:
		case RIGHT:
			return false;
		case DOWN_RIGHT:
			robot.setTurnRight(Math.PI / 2);
			robot.setAhead(100);
			return true;
		case DOWN:
			robot.setTurnRight(Math.PI / 2);
			robot.setBack(100);
			return true;
		case LEFT:
		case DOWN_LEFT:
		case TOP_LEFT:
			robot.setTurnLeft(Math.PI / 2);
			robot.setBack(100);
			return true;
		}
		return false;
	}

	/**
	 * 当机器人的机身方向面对的是270~360度时，处于地图的loc的位置，应当怎么躲避撞墙
	 * 
	 * @param loc 机器人处于地图中的位置
	 * @return 是否可能会撞墙
	 */
	public static boolean avoid4(RobotLocation loc) {
		GodTank robot = GodTank.getInstance();
		switch (loc) {
		case CENTER:
		case DOWN_RIGHT:
		case DOWN:
		case RIGHT:
			return false;
		case DOWN_LEFT:
		case LEFT:
			robot.setTurnRight(Math.PI / 2);
			robot.setBack(100);
			return true;
		case TOP_RIGHT:
		case TOP:
		case TOP_LEFT:
			robot.setTurnLeft(Math.PI / 2);
			robot.setBack(100);
			return true;
		}
		return false;
	}
}
