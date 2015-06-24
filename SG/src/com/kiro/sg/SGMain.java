package com.kiro.sg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.kiro.sg.crates.CrateUtils;
import com.kiro.sg.listeners.EntityDamage;
import com.kiro.sg.listeners.PlayerLeaveArena;
import com.kiro.sg.listeners.PlayerMove;
import com.kiro.sg.listeners.SignManager;
import com.kiro.sg.listeners.items.CarePackageUse;
import com.kiro.sg.listeners.items.HungerRefillCake;
import com.kiro.sg.listeners.items.NetherstarHeal;
import com.kiro.sg.listeners.items.RedstoneHealthBonus;
import com.kiro.sg.listeners.items.Slowball;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

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
		
		pm.registerEvents(new Slowball(), this);
		pm.registerEvents(new CarePackageUse(), this);
		pm.registerEvents(new HungerRefillCake(), this);
		pm.registerEvents(new NetherstarHeal(), this);
		pm.registerEvents(new RedstoneHealthBonus(), this);
		
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