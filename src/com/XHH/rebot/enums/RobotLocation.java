package com.XHH.rebot.enums;

/**
 * 表达机器人位于什么位置的枚举
 * @author 郭江龙
 *
 */
public enum RobotLocation {
	TOP_LEFT("左上", 1),    //左上
	TOP_RIGHT("右上", 2),   //右上
	DOWN_LEFT("左下", 3),   //左下
	DOWN_RIGHT("右下", 4),  //右下
	TOP("上", 5),          //上
	DOWN("下", 6),         //下
	LEFT("左", 7),         //左
	RIGHT("右", 8),        //右
	CENTER("中", 9);       //中
	
	private String name;  //中文名称
	private int index;    //序号
	
	//getter
	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	/**
	 * 枚举的构造方法
	 * @param name 中文名称
	 * @param index 序号
	 */
	RobotLocation(String name, int index) {
		this.name = name;
		this.index = index;
	}
}
