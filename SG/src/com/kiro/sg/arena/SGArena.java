package com.kiro.sg.arena;

import com.kiro.sg.Config;
import com.kiro.sg.utils.CleanWorldGenerator;
import com.kiro.sg.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class SGArena
{

	private static final Pattern COMPILE = Pattern.compile(" ", Pattern.LITERAL);
	private String arenaName;
	private String worldName;

	private World world;

	private File worldInUse;

	private List<Location> spawnPoints;
	private List<Location> cornChests;
	private Location centerPoint;

	private static int WorldIndex;
	private int arenaID;

	public SGArena(String arenaName, String worldName)
	{
		this.arenaName = arenaName;
		this.worldName = worldName;
		arenaID = WorldIndex++;
	}

	public SGArena(String arenaName)
	{
		this.arenaName = arenaName;
	}

	/**
	 * The world that the arena is using.
	 */
	public World getWorld()
	{
		return world;
	}

	public String getArenaName()
	{
		return arenaName;
	}

	public void addSpawn(Location loc)
	{
		if (spawnPoints == null)
		{
			spawnPoints = new ArrayList<>();
		}
		spawnPoints.add(loc);
	}

	public void addCornChest(Location loc)
	{
		if (cornChests == null)
		{
			cornChests = new ArrayList<>();
		}
		cornChests.add(loc);
	}

	public List<Location> getCornChests()
	{
		return cornChests;
	}

	public Location getCenterPoint()
	{
		return centerPoint;
	}

	public void setCenterPoint(Location loc)
	{
		centerPoint = loc;
	}

	/**
	 * generates the world.
	 */
	public void createWorld()
	{
		try
		{

			FileUtils.copyFolder(new File(Config.ArenaWorldFolder, worldName), getWorldInUseFolder());

			WorldCreator wc = WorldCreator.name(getWorldInUseFolder().getName());
			wc.generator(new CleanWorldGenerator(0, 0));

			world = wc.createWorld();
			world.setAutoSave(false);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void dispose()
	{
		Location location = Bukkit.getWorlds().get(0).getSpawnLocation();
		for (Player player : world.getPlayers())
		{
			player.teleport(location);
		}
		if (Bukkit.unloadWorld(getWorld(), false))
		{
			FileUtils.deleteFolder(getWorldInUseFolder());
		}
		else
		{
			System.out.println("Couldn't Unload World! " + world.getName());
		}
	}

	/**
	 * Loads the arena data as well as generates the world.
	 */
	public void loadArena()
	{
		try
		{
			YamlConfiguration config = YamlConfiguration.loadConfiguration(getArenaFileLocation());
			arenaName = config.getString("arenaName");
			worldName = config.getString("worldName");

			createWorld();

			centerPoint = Config.getLocation(config.getConfigurationSection("center"), world);

			ConfigurationSection spawns = config.getConfigurationSection("spawns");

			Set<String> keys = spawns.getKeys(false);
			spawnPoints = new ArrayList<>(keys.size());
			for (String key : keys)
			{
				Location loc = Config.getLocation(spawns.getConfigurationSection(key), world).add(0.5, 0.5, 0.5);
				spawnPoints.add(loc);
			}

			if (config.contains("cornChests"))
			{
				ConfigurationSection cornChestConfig = config.getConfigurationSection("cornChests");

				keys = cornChestConfig.getKeys(false);
				cornChests = new ArrayList<>(keys.size());
				for (String key : keys)
				{
					Location loc = Config.getLocation(cornChestConfig.getConfigurationSection(key), world);
					cornChests.add(loc);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Saves the arena
	 */
	public void saveArena()
	{
		try
		{
			YamlConfiguration config = YamlConfiguration.loadConfiguration(getArenaFileLocation());

			config.set("arenaName", arenaName);
			config.set("worldName", worldName);

			Config.saveLocation(config.createSection("center"), centerPoint);

			ConfigurationSection spawns = config.createSection("spawns");

			for (int i = 0; i < spawnPoints.size(); i++)
			{
				Config.saveLocation(spawns.createSection("s" + i), spawnPoints.get(i));
			}

			ConfigurationSection cornChestConfig = config.createSection("cornChests");

			for (int i = 0; i < cornChests.size(); i++)
			{
				Config.saveLocation(cornChestConfig.createSection("s" + i), cornChests.get(i));
			}

			config.save(getArenaFileLocation());

			ArenaManager.getInstance().addArena(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * gets the spawnpoints
	 */
	public List<Location> getSpawns()
	{
		return spawnPoints;
	}

	/**
	 * @return The folder that contains the map copy, delete after the game ends.
	 */
	private File getWorldInUseFolder()
	{
		if (worldInUse == null)
		{
			worldInUse = new File("_map_" + arenaID);
		}
		return worldInUse;
	}

	/**
	 * @return the file that contains the arena data
	 */
	private File getArenaFileLocation()
	{
		return new File(Config.ArenaConfigFolder, COMPILE.matcher(arenaName).replaceAll("_") + ".yml");
	}

	/**
	 * @return a copy of an SGArena that can be used for a game and generate the world.
	 */
	public SGArena createNew()
	{
		return new SGArena(arenaName);
	}

	/**
	 * Compares if it's the same instance OR if the name provided equals.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SGArena)
		{
			return ((SGArena) obj).arenaID == arenaID;
		}
		return arenaName.equals(obj);
	}

}
