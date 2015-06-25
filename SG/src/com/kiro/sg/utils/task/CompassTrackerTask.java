package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CompassTrackerTask extends BukkitRunnable
{

	private static final long FIND_NEAREST_PLAYER_TICKS = 40;
	private final Player player;

	public CompassTrackerTask(Player player)
	{
		this.player = player;

		runTaskTimer(SGMain.getPlugin(), 1, FIND_NEAREST_PLAYER_TICKS);
	}

	@Override
	public void run()
	{
		Location playerLoc = player.getLocation();

		Player closest = null;
		double lastDist = 0D;

		for (Player player : Bukkit.getOnlinePlayers())
		{
			if (!player.equals(this.player) && player.getGameMode() == GameMode.SURVIVAL && !player.isSneaking())
			{
				double dist = playerLoc.distanceSquared(player.getLocation());
				if (closest == null || dist < lastDist)
				{
					closest = player;
					lastDist = dist;
				}
			}
		}

		if (closest != null)
		{
			player.setCompassTarget(closest.getLocation());
		}
		else
		{
			player.setCompassTarget(playerLoc);
		}
	}
}