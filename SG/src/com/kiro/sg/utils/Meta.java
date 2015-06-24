package com.kiro.sg.utils;

import com.kiro.sg.SGMain;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Meta
{

	public static void setMetadata(Player player, String key, Object obj)
	{
		player.setMetadata(key, new FixedMetadataValue(SGMain.getPlugin(), obj));
	}

	public static void removeMetadata(Player player, String key)
	{
		if (has(player, key))
		{
			player.removeMetadata(key, SGMain.getPlugin());
		}
	}

	public static Object getMetadata(Player player, String key)
	{
		if (has(player, key))
		{
			return player.getMetadata(key).get(0).value();
		}
		return null;
	}

	public static boolean has(Player player, String key)
	{
		return player.hasMetadata(key);
	}


}
