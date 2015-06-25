package com.kiro.sg.config;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public final class Config
{
	public static final File ConfigFolder = new File("./plugins/SurvivalGames");
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

	public static Material[] BREAKABLE_BLOCKS = {Material.LEAVES, Material.LEAVES_2, Material.GRASS, Material.YELLOW_FLOWER,
			                                            Material.RED_ROSE, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM,
			                                            Material.CROPS, Material.DEAD_BUSH, Material.LONG_GRASS};
	public static Material[] PLACEABLE_BLOCKS = {Material.TORCH, Material.TNT};

	private Config()
	{
	}


	public static Location getLocation(ConfigurationSection config, World world)
	{
		double px = config.getInt("px");
		double py = config.getInt("py");
		double pz = config.getInt("pz");

		return new Location(world, px, py, pz);
	}

	public static void saveLocation(ConfigurationSection config, Location loc)
	{
		config.set("px", loc.getBlockX());
		config.set("py", loc.getBlockY());
		config.set("pz", loc.getBlockZ());
	}

}
