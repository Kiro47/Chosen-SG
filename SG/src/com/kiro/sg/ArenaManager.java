package com.kiro.sg;

import com.kiro.sg.arena.SGArena;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArenaManager
{

	List<SGArena> arenaList;

	public void init()
	{
		arenaList = new ArrayList<>();
		loadArenas();
	}

	/**
	 * Loads only the arena names - loading all the extra info at this time would be a waste of resources.
	 */
	private void loadArenas()
	{
		arenaList.clear();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.ArenaListFile);

		Set<String> keys = config.getKeys(false);
		for (String key : keys)
		{
			if (config.getBoolean(key + ".enabled"))
			{
				SGArena arena = new SGArena(key);
				arenaList.add(arena);
			}
		}
	}

	public SGArena getArena(String name)
	{
		return new SGArena(name);
	}

	/**
	 * Used when defining a new arena, adds the arena to the list
	 */
	public void addArena(SGArena arena)
	{
		try
		{
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.ArenaListFile);
			config.set(arena.getArenaName() + ".enabled", true);

			config.save(Config.ArenaListFile);
			arenaList.add(arena.createNew());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Enables or disables a given arena.
	 */
	public boolean enableArena(String name, boolean enabled)
	{
		try
		{
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.ArenaListFile);
			if (config.contains(name))
			{
				config.set(name + ".enabled", enabled);

				config.save(Config.ArenaListFile);

				if (enabled)
				{
					arenaList.add(new SGArena(name));
				}
				else
				{
					arenaList.remove(name);
				}

				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}


	private static ArenaManager instance = new ArenaManager();

	public static ArenaManager getInstance()
	{
		return instance;
	}
	//
	//	private ArrayList<Arena> arenas;
	//
	//	private ArenaManager()
	//	{
	//		this.arenas = new ArrayList<Arena>();
	//	}
	//
	//
	//	public void setup()
	//	{
	//		arenas.clear();
	//
	//		for (String arenaID : SettingsManager.getArenas().getKeys())
	//		{
	//			arenas.add(new Arena(arenaID));
	//		}
	//	}
	//
	//	public Arena getArena(String id)
	//	{
	//		for (Arena arena : arenas)
	//		{
	//			if (arena.getID().equals(id))
	//			{
	//				return arena;
	//			}
	//		}
	//		return null;
	//	}
	//
	//	public ArrayList<Arena> getArenas()
	//	{
	//		return arenas;
	//	}
	//
	//	public Arena getArena(Player p)
	//	{
	//		for (Arena arena : arenas)
	//		{
	//			if (arena.hasPlayer(p))
	//			{
	//				return arena;
	//			}
	//		}
	//		return null;
	//	}
}
