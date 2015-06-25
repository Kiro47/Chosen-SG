package com.kiro.sg.utils.misc;

import org.bukkit.event.block.Action;

public class EventUtils
{

	public static boolean isRightClick(Action action)
	{
		return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
	}

	public static boolean isLeftClick(Action action)
	{
		return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
	}

	public static boolean isClickAir(Action action)
	{
		return action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR;
	}

	public static boolean isClickBlock(Action action)
	{
		return action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK;
	}

}
