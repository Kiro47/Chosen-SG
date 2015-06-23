package com.kiro.sg;

import com.kiro.sg.crates.CrateUtils;
import com.kiro.sg.listeners.EntityDamage;
import com.kiro.sg.listeners.PlayerLeaveArena;
import com.kiro.sg.listeners.PlayerMove;
import com.kiro.sg.listeners.SignManager;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SGMain extends JavaPlugin
{


	public void onEnable()
	{
		ArenaManager.getInstance().setup();

		CrateUtils.loadLoots();

		getCommand("survivalgames").setExecutor(new CommandManager());

		PluginManager pm = Bukkit.getServer().getPluginManager();

		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerLeaveArena(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new SignManager(), this);

	}

	public static Plugin getPlugin()
	{
		return Bukkit.getServer().getPluginManager().getPlugin("survivalgames");
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