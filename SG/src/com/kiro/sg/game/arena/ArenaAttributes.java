package com.kiro.sg.game.arena;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ArenaAttributes
{

	public enum TimeCycle
	{
		ALL_DAY, ALL_NIGHT, NORMAL;
	}

	private boolean containsAttributes;

	private boolean hasPotionEffects;
	private List<PotionEffect> potionEffects;
	private TimeCycle timeCycle;

	public void load(YamlConfiguration config)
	{
		timeCycle = TimeCycle.NORMAL;
		if (config.contains("attributes"))
		{
			containsAttributes = true;

			List<String> list = config.getStringList("attributes.potions");
			if (!list.isEmpty())
			{
				hasPotionEffects = true;
				potionEffects = new ArrayList<>();
				for (String line : list)
				{
					String[] args = line.split(",");

					PotionEffectType type = PotionEffectType.getByName(args[0]);
					potionEffects.add(new PotionEffect(type, 100000, Integer.valueOf(args[1])));
				}
			}

			timeCycle = TimeCycle.valueOf(config.getString("attributes.world.time_cycle", "NORMAL"));
		}
	}

	public boolean hasAttributes()
	{
		return containsAttributes;
	}

	public void setPotionEffects(Player player)
	{
		if (hasPotionEffects)
		{
			player.addPotionEffects(potionEffects);
		}
	}

	public TimeCycle timeCycle()
	{
		return timeCycle;
	}

}
