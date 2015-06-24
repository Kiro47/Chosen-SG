package com.kiro.sg.utils;

import com.kiro.sg.SGMain;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageTracker extends BukkitRunnable
{

	public static void set(Player damaged, Player damager)
	{
		DamageTracker tracker = (DamageTracker) Meta.getMetadata(damaged, "ldb");
		if (tracker == null)
		{
			tracker = new DamageTracker();
			Meta.setMetadata(damaged, "ldb", tracker);
		}

		tracker.setDamagedBy(damager);
	}


	public static Player get(Player damaged)
	{
		DamageTracker tracker = (DamageTracker) Meta.getMetadata(damaged, "ldb");
		if (tracker == null)
		{
			return null;
		}

		return tracker.getDamagedBy();
	}

	public static void remove(Player player)
	{
		DamageTracker tracker = (DamageTracker) Meta.getMetadata(player, "ldb");
		if (tracker != null)
		{
			tracker.cancel();
			Meta.removeMetadata(player, "ldb");
		}
	}

	private int ticks;
	private Player damagedBy;

	public DamageTracker()
	{
		runTaskTimer(SGMain.getPlugin(), 20, 20);
	}

	public void setDamagedBy(Player player)
	{
		ticks = 0;
		damagedBy = player;
	}

	public Player getDamagedBy()
	{
		return damagedBy;
	}

	@Override
	public void run()
	{
		if (ticks++ == 6)
		{
			damagedBy = null;
		}
	}
}
