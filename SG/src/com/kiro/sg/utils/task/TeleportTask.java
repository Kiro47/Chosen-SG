package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TeleportTask extends BukkitRunnable
{

	private final List<Player> players;
	private final List<Location> spawns;
	private int index;

	public TeleportTask(List<Player> players, List<Location> spawns)
	{
		this.players = players;
		this.spawns = spawns;
		index = 0;

		runTaskTimer(SGMain.getPlugin(), 1, 1);
	}

	@Override
	public void run()
	{
		try
		{
			players.get(index).teleport(spawns.get(index));

			index++;
			if (index == players.size())
			{
				cancel();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			cancel();
		}
	}
}
