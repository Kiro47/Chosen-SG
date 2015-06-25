package com.kiro.sg.utils.task;

import com.kiro.sg.SGMain;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworksTask extends BukkitRunnable
{

	private final Player player;
	private int count;

	public FireworksTask(Player player)
	{
		this.player = player;
		count = 19;

		runTaskTimer(SGMain.getPlugin(), 10, 10);
	}

	@Override
	public void run()
	{
		if (count-- == 0 || !player.isOnline())
		{
			cancel();
		}
		else
		{
			World world = player.getWorld();
			Firework fw = (Firework) world.spawnEntity(player.getLocation(), EntityType.FIREWORK);
			FireworkMeta meta = fw.getFireworkMeta();
			//meta.addEffect();
			FireworkEffect.Builder builder = FireworkEffect.builder();
			builder.with(FireworkEffect.Type.values()[(int) (Math.random() * FireworkEffect.Type.values().length)]);
			int c = (int) (Math.random() * 3) + 1;
			for (int i = 0; i < c; i++)
			{
				builder.withColor(Color.fromRGB((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			}
			c = (int) (Math.random() * 3);
			for (int i = 0; i < c; i++)
			{
				builder.withFade(Color.fromRGB((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			}

			builder.trail((int) (Math.random() * 4) == 1);
			builder.flicker((int) (Math.random() * 4) == 1);

			meta.addEffect(builder.build());
			meta.setPower((int) (Math.random() * 2));

			fw.setFireworkMeta(meta);

		}
	}

}
