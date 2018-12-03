package com.XHH.rebot;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.XHH.rebot.engine.MainEngine;
import com.XHH.rebot.enums.RobotLocation;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * 无可抵挡的机器人。
 * 
 * @author 郭江龙，邓港大，黎梓晟，李振豪，刘展坤
 */
public final class GodTank extends AdvancedRobot {
	MyEnemy target; // 当前敌人的对象
//	boolean isBlock = false;
//	int count = 0;

	private final int edge = 100;
//	private final int leave = 450;
//	private final double trifix = 90;

//	Rectangle2D playField;
//	double lastEnemyHeading, firePower, targetEnergy = 100;
//	int radarturn = 1;
//
//	int cnt;

	/**
	 * 当对象被创建的时候，就应当执行的初始化操作
	 */
	public GodTank() {
		if (!MyEnemy.isInit())
			MyEnemy.init();
		target = MyEnemy.getInstance();
	}

	/**
	 * 初始化操作
	 */
	private void init() {
		setColors(Color.BLUE, Color.GREEN, Color.RED);
		setMaxVelocity(Double.MAX_VALUE);
		setMaxTurnRate(Double.MAX_VALUE);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
	}

	private void avoidHitWall(RobotLocation loc, double degree) {
		int headLoc = (int) degree / 45;
		switch (loc) {
		case DOWN_LEFT:
			if (degree) {

			}
		}
//		if (b1) {
//			if (b2) {
//				setTurnLeft(trifix);
//			} else {
//				setTurnRight(trifix);
//			}
//			setBack(leave);
//		} else {
//			setTurnLeft(trifix);
//			setAhead(leave);
//		}
	}

	private RobotLocation calcWhereRobotBeIn(double x, double y) {
		double top = getBattleFieldHeight() - edge;
		double down = edge;
		double left = edge;
		double right = getBattleFieldWidth() - edge;
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

	@Override
	public void run() {
		init();
		MainEngine.mainCycle(this);
		while (true) {
			long startTime = System.currentTimeMillis();
			double x = getX(), y = getY(), degree = getHeading();
//			if (x < edge) {
//				System.out.println("a");
//				avoidFromWall(degree >= 180 && degree <= 360, degree <= 270);
//			} else if (y < edge) {
//				System.out.println("b");
//				avoidFromWall(degree >= 90 && degree <= 270, degree <= 180);
//			} else if (x > getBattleFieldWidth() - edge) {
//				System.out.println("d");
//				avoidFromWall(degree >= 0 && degree <= 180, degree < 90);
//			} else if (y > getBattleFieldHeight() - edge) {
//				System.out.println("c");
//				avoidFromWall(degree >= 270 || degree <= 90, degree >= 270);
//			} else if (((count/10)&1) == 0) {
//				System.out.println("e");
//				setTurnLeft(trifix*3);
//				setAhead(leave);
//			} else {
//				System.out.println("f");
//				setTurnRight(trifix*3);
//				setAhead(leave);
//			}
//			setTurnRadarLeft(25);
//			doScan();
//			doRamFire();
//			++count;
//			execute();
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime) + "ms");
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.getName().equals(target.getName()) || (e.getDistance() < target.getDistance())) {
			target.update(e, this);
		}

	}

	@Override
	public void onHitRobot(HitRobotEvent e) {
		double offset = Utils.normalRelativeAngle(e.getBearingRadians() + getGunHeadingRadians() - Math.PI / 18);
		setTurnGunRightRadians(offset);
		for (int i = 0; i < 4; ++i) {
			setFire(3);
		}
		setTurnRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI));
	}

	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		target.setDistance(10000);
		target.setEnable(false);
	}

	public void doRamFire() {
		if (!target.isEnable())
			return;
		double power = Math.min(3, 400 / target.getDistance());
		double rVelocity = target.getVelocity();
		double bVelocity = 20 - 3 * power;
		double direction = getHeadingRadians() + target.getBearing();

		double maxAngle = Math.asin(rVelocity / bVelocity);

		double fireOffset = Math.random() * maxAngle;
		if (Utils.normalRelativeAngle(target.getHeading() - direction) < 0)
			fireOffset *= -1;
		double gunOffset = Utils.normalRelativeAngle(direction - getGunHeadingRadians() + fireOffset);

		setTurnGunRightRadians(gunOffset);
		setFire(power);
	}

	public void doScan() {
		double radarOffset = Utils
				.normalRelativeAngle(getHeadingRadians() + target.getBearing() - getRadarHeadingRadians());
		setTurnRadarRight(Math.toDegrees(radarOffset) * 1.1);
	}
}
