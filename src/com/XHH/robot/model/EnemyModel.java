package com.XHH.robot.model;

import com.XHH.robot.entity.Enemy;

/**
 * 遵从OOP原则，采用分类模块化编程。
 * <p>
 * 当前目标敌人的操作，工具类
 * 
 * @author 郭江龙
 */
public class EnemyModel {
	private static Enemy nowEnemy; // 当前的敌人

	/**
	 * 获取当前的敌人信息
	 * 
	 * @return 当前的敌人
	 */
	public static Enemy getNowTarget() {
		return nowEnemy;
	}

	/**
	 * 清空当前的敌人信息，以转换下一个目标并停火
	 * <p>
	 * 用于回合结束，或者敌人死亡
	 */
	public static void clear() {
		nowEnemy = null;
	}

	/**
	 * 工具类不可能有实例
	 */
	private EnemyModel() {
		throw new IllegalArgumentException("EnemyModel工具类不可能有实例！");
	}

	/**
	 * 返回当前有没有目标敌人
	 * 
	 * @return 是否存在目标敌人
	 */
	public static boolean existEnemy() {
		return nowEnemy == null;
	}

	/**
	 * 发现敌人之后的更新信息操作
	 * 
	 * @param newEnemy 新的敌人
	 */
	public static void update(Enemy newEnemy) {
		nowEnemy = newEnemy;
	}
}
