package com.kiro.sg;

import com.kiro.sg.commands.CommandManager;
import com.kiro.sg.game.arena.ArenaManager;
import com.kiro.sg.game.crates.CrateUtils;
import com.kiro.sg.game.lobby.LobbyManager;
import com.kiro.sg.listeners.*;
import com.kiro.sg.utils.chat.Chat;
import com.kiro.sg.utils.misc.FileUtils;
import com.kiro.sg.voting.VotingMap;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SGMain extends JavaPlugin
{
	private static SGMain instance;
	private LobbyManager lobbyManager;

	public void onEnable()
	{
		instance = this;

		CrateUtils.loadLoots();
		Chat.init();

		getCommand("survivalgames").setExecutor(new CommandManager());

		PluginManager pm = Bukkit.getServer().getPluginManager();

		// custom events
		pm.registerEvents(new CustomEventsListener(), this);

		pm.registerEvents(new LobbyListener(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerLeaveArena(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new CrateListener(), this);


		// Items
		pm.registerEvents(new CustomItemsListener(), this);
		//		pm.registerEvents(new HungerRefillCake(), this);
		//		pm.registerEvents(new NetherstarHeal(), this);
		//pm.registerEvents(new RedstoneHealthBonus(), this);
		//		pm.registerEvents(new Slowball(), this);
		// Items

		pm.registerEvents(new SpectatorListner(), this);
		pm.registerEvents(new BlockListener(), this);

		ArenaManager.getInstance().init();
		VotingMap.loadMaps();

		lobbyManager = new LobbyManager();

		// delete unused worlds
		File[] files = new File("./").listFiles();
		for (File file : files)
		{
			if (file.getName().contains("_map_"))
			{
				FileUtils.deleteFolder(file);
			}
		}

	}

	public LobbyManager getLobbyManager()
	{
		return lobbyManager;
	}

	public static SGMain getPlugin()
	{
		return instance;
	}

	public static WorldEditPlugin getWorldEdit()
	{
		return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	}

	public static void saveLocation(Location location, ConfigurationSection section)
	{
		section.set("world", location.getWorld().getName());
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("pitch", location.getPitch());
		section.set("yaw", location.getYaw());
	}

	public static Location loadLocation(ConfigurationSection section)
	{
		return new Location(
				                   Bukkit.getServer().getWorld(section.getString("world")),
				                   section.getDouble("x"),
				                   section.getDouble("y"),
				                   section.getDouble("z"),
				                   (float) section.getDouble("pitch"),
				                   (float) section.getDouble("yaw")
		);
	}
}