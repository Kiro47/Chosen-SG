package com.kiro.sg.lobby.leaderboards;

import com.kiro.sg.config.Config;
import com.kiro.sg.mysql.Consumer;
import com.kiro.sg.mysql.query.queries.top.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public final class LeaderBoard
{

	public static void init()
	{
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.TopListConfigFile);
		if (config.contains("kills"))
		{
			new LeaderboardEntry(config.getConfigurationSection("kills"), GetTopKillsQuery.class);
		}
		if (config.contains("wins"))
		{
			new LeaderboardEntry(config.getConfigurationSection("wins"), GetTopWinsQuery.class);
		}
		if (config.contains("kills_per_game"))
		{
			new LeaderboardEntry(config.getConfigurationSection("kills_per_game"), GetTopKillsPerGameQuery.class);
		}
		if (config.contains("games"))
		{
			new LeaderboardEntry(config.getConfigurationSection("games"), GetTopGamesQuery.class);
		}
		if (config.contains("score"))
		{
			new LeaderboardEntry(config.getConfigurationSection("score"), GetTopScoreQuery.class);
		}
	}

	public static void addLoc(String path, String var, Location location)
	{
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.TopListConfigFile);
		ConfigurationSection section;

		if (config.contains(path))
		{
			section = config.getConfigurationSection(path);
		}
		else
		{
			section = config.createSection(path);
		}

		section.set("world", location.getWorld().getName());
		Config.saveLocation(var, section, location);

		try
		{
			config.save(Config.TopListConfigFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public static class LeaderboardEntry
	{
		private static final String[] place = {ChatColor.DARK_GREEN + "1st Place", ChatColor.DARK_GREEN + "2nd Place", ChatColor.DARK_GREEN + "3rd Place"};
		private final TopEntry[] topData = {null, null, null};
		private final Location[] skull_loc = {null, null, null};
		private final Location[] sign_loc = {null, null, null};
		private int index;

		public LeaderboardEntry(ConfigurationSection section, Class<? extends TopQuery> clz)
		{
			String w = section.getString("world");
			World world = Bukkit.getWorld(w);

			skull_loc[0] = Config.getLocation("skull_0", section, world);
			skull_loc[1] = Config.getLocation("skull_1", section, world);
			skull_loc[2] = Config.getLocation("skull_2", section, world);

			sign_loc[0] = Config.getLocation("sign_0", section, world);
			sign_loc[1] = Config.getLocation("sign_1", section, world);
			sign_loc[2] = Config.getLocation("sign_2", section, world);


			try
			{
				TopQuery query = clz.getConstructor(LeaderboardEntry.class).newInstance(this);
				Consumer.queue(query);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		public void addEntry(TopEntry user)
		{
			topData[index++] = user;
		}

		public void doneLoading()
		{
			for (int i = 0; i < 3; i++)
			{
				Location skullLoc = skull_loc[i];
				Location signLoc = sign_loc[i];
				TopEntry entry = topData[i];
				if (entry == null || skullLoc == null || signLoc == null)
				{
					break;
				}

				if (!skullLoc.getChunk().isLoaded())
				{
					skullLoc.getChunk().load();
				}

				Block block = signLoc.getBlock();
				if (block.getState() instanceof Sign)
				{
					Sign sign = (Sign) block.getState();

					sign.setLine(0, place[i]);
					sign.setLine(1, entry.getName());

					//noinspection FloatingPointEquality
					if (entry.getValue() == (int) entry.getValue())
					{
						sign.setLine(2, String.valueOf((int) entry.getValue()));
					}
					else
					{
						sign.setLine(2, String.valueOf(entry.getValue()));
					}

					sign.update();
				}

				block = skullLoc.getBlock();
				if (block.getState() instanceof Skull)
				{
					Skull skull = (Skull) block.getState();
					skull.setSkullType(SkullType.PLAYER);
					skull.setOwner(entry.getName());

					skull.update();
				}

			}
		}
	}
}
