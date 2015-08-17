package com.kiro.sg;

import com.kiro.sg.commands.CommandManager;
import com.kiro.sg.config.Config;
import com.kiro.sg.game.arena.ArenaManager;
import com.kiro.sg.game.crates.CrateUtils;
import com.kiro.sg.listeners.ChatListener;
import com.kiro.sg.listeners.CommandListner;
import com.kiro.sg.listeners.CustomEventsListener;
import com.kiro.sg.listeners.EntityDamage;
import com.kiro.sg.listeners.game.BlockListener;
import com.kiro.sg.listeners.game.CrateListener;
import com.kiro.sg.listeners.game.CustomItemsListener;
import com.kiro.sg.listeners.game.PlayerMove;
import com.kiro.sg.listeners.lobby.LobbyListener;
import com.kiro.sg.listeners.lobby.LoginListener;
import com.kiro.sg.listeners.lobby.PlayerLeaveArena;
import com.kiro.sg.listeners.lobby.StatusSignListener;
import com.kiro.sg.listeners.spectator.SpectatorListner;
import com.kiro.sg.listeners.spectator.SpectatorMenuListener;
import com.kiro.sg.lobby.GameSign;
import com.kiro.sg.lobby.LobbyManager;
import com.kiro.sg.lobby.leaderboards.LeaderBoard;
import com.kiro.sg.lobby.voting.VotingMap;
import com.kiro.sg.mysql.Consumer;
import com.kiro.sg.mysql.MySQL;
import com.kiro.sg.utils.Meta;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.nms.BorderUtils;
import com.kiro.sg.utils.misc.FileUtils;
import com.kiro.sg.nms.ReflectionUtils;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.SQLException;

public class SGMain extends JavaPlugin
{
	private static SGMain instance;
	private LobbyManager lobbyManager;

	private MySQL db;
	private Consumer consumer;

	@Override
	public void onEnable()
	{
		instance = this;

		CrateUtils.loadLoots();
		ChatUtils.init();

		db = new MySQL(Config.ConfigFolder);
		consumer = new Consumer(this, db);


		getCommand("survivalgames").setExecutor(new CommandManager());

		PluginManager pm = Bukkit.getServer().getPluginManager();

		// custom events
		pm.registerEvents(new CustomEventsListener(), this);

		pm.registerEvents(new LobbyListener(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerLeaveArena(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new CrateListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new CustomItemsListener(), this);
		pm.registerEvents(new SpectatorListner(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new CommandListner(), this);
		pm.registerEvents(new SpectatorMenuListener(), this);
		pm.registerEvents(new StatusSignListener(), this);
		pm.registerEvents(new LoginListener(), this);

		GameSign.loadConfig();
		GameSign.updateSigns();


		ArenaManager.getInstance().init();
		VotingMap.loadMaps();

		lobbyManager = new LobbyManager();

		ReflectionUtils.getVersion();
		BorderUtils.init();
		World world = Bukkit.getWorlds().get(0);
		BorderUtils.assignNew(world);

		WorldBorder border = world.getWorldBorder();
		border.setDamageAmount(0.0);
		border.setDamageBuffer(0);
		border.setCenter(world.getSpawnLocation());
		border.setSize(200);

		LeaderBoard.init();

		// delete unused worlds
		File[] files = new File("./").listFiles();
		for (File file : files)
		{
			if (file.getName().contains("_map_"))
			{
				FileUtils.deleteFolder(file);
			}
		}

		new BukkitRunnable()
		{

			World world;

			@Override
			public void run()
			{
				if (world == null)
				{
					world = Bukkit.getWorlds().get(0);
				}

				Location location = new Location(world, 590, 71, 795);
				if (location.getChunk().isLoaded())
				{
					Block block = location.getBlock();
					if (block.getType() == Material.STAINED_GLASS)
					{
						block.setData((byte) (Math.random() * 16));
					}
				}
			}
		}.runTaskTimer(this, 20, 40);

	}

	@Override
	public void onDisable()
	{

		for (Player player : Bukkit.getOnlinePlayers())
		{
			Meta.removeMetadata(player, "game");
			Meta.removeMetadata(player, "stats");
		}

		consumer.stop();
		try
		{
			db.getConnection().close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
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