package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CompassTrackerTask extends BukkitRunnable
{

	private static final long FIND_NEAREST_PLAYER_TICKS = 40;
	private final Player player;
	private final GameInstance instance;

	public CompassTrackerTask(Player player)
	{
		this.player = player;
		instance = GameManager.getInstance(player);

		runTaskTimer(SGMain.getPlugin(), 1, FIND_NEAREST_PLAYER_TICKS);
	}

	@Override
	public void run()
	{
		Location playerLoc = player.getLocation();

		Player closest = null;
		double lastDist = 0D;

		for (Player player : instance.getRemaining())
		{
			try
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
			catch (Exception e)
			{
				
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