package com.kiro.sg;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public final class Config
{
	public static final File ConfigFolder = new File("./plugin/SurvivalGames");
	public static final File LootConfigFile = new File(ConfigFolder, "loot.yml");

	public static final File ArenaListFile = new File(ConfigFolder, "arenas.yml");
	public static final File VotingMapFile = new File(ConfigFolder, "voting.yml");
	public static final File ArenaConfigFolder = new File(ConfigFolder, "/arenas/");
	public static final File ArenaWorldFolder = new File(ConfigFolder, "/maps/");
	public static final File ArenaImagesFolder = new File(ConfigFolder, "/images/");

	public static int MIN_PLAYER_COUNT = 1; // 1 for testing
	public static int MAX_PLAYER_COUNT = 24;

	public static int TIMER_STARTING_COUNTDOWN = 25; // 25 seconds
	public static int TIMER_GAME_MAX_TIME = 1200; // 20 min
	public static int TIMER_DEATHMATCH_MAX = 300; // 5 mins
	public static int TIMER_CHEST_REFILL = 600; // 10 mins

	private Config()
	{
	}


	public static Location getLocation(ConfigurationSection config, World world)
	{
		double px = config.getInt("px") + 0.5;
		double py = config.getInt("py") + 0.5;
		double pz = config.getInt("pz") + 0.5;

		return new Location(world, px, py, pz);
	}

	public static void saveLocation(ConfigurationSection config, Location loc)
	{
		config.set("px", loc.getBlockX());
		config.set("py", loc.getBlockY());
		config.set("pz", loc.getBlockZ());

	}

}
