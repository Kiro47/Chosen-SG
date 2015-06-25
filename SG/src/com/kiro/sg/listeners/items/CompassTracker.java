package com.kiro.sg.listeners.items;

import org.bukkit.entity.Player;

import java.util.Comparator;

public class CompassTracker implements Comparator<Player>
{


	private final Player player;

	public CompassTracker(Player player)
	{
		this.player = player;
	}

	@Override
	public int compare(Player targ1, Player targ2)
	{
		return compare(
				              targ1.getLocation().distanceSquared(player.getLocation()),
				              targ2.getLocation().distanceSquared(player.getLocation()));
	}

	private int compare(double a, double b)
	{
		return a > b ? -1 : a > b ? 1 : 0;
	}

}
