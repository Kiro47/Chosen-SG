package com.kiro.sg;

import com.kiro.sg.arena.SGArena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

public class LobbySign
{

	private Location loc;
	private Sign sign;
	private SGArena arena;

	public LobbySign(Location loc, SGArena arena)
	{
		this.loc = loc;
		this.sign = (Sign) loc.getBlock().getState();
		this.arena = arena;
	}

	public Location getLocation()
	{
		return loc;
	}

	public Sign getSign()
	{
		return sign;
	}

	public SGArena getArena()
	{
		return arena;
	}

	public void update()
	{
		String s = "[" + ChatColor.RED + "SurvivalGames" + ChatColor.RESET + "]";
		sign.setLine(0, s);
		sign.setLine(1, arena.getArenaName());
		//		sign.setLine(2, arena.getState().toString().toLowerCase().substring(0, 1).toUpperCase() + arena.getState().toString().toLowerCase().substring(1));
		//		if (arena.getPlayers().length != arena.getMaxPlayers())
		//		{
		//			sign.setLine(3, ChatColor.GREEN + "" + arena.getPlayers().length + "/" + arena.getMaxPlayers());
		//		}
		//		else
		//		{
		//			sign.setLine(3, ChatColor.RED + "" + arena.getPlayers().length + "/" + arena.getMaxPlayers());
		//		}
		sign.update();
	}

	public void save(ConfigurationSection section)
	{
		section.set("location.world", loc.getWorld().getName());
		section.set("location.x", loc.getX());
		section.set("location.y", loc.getY());
		section.set("location.z", loc.getZ());
		section.set("arena", arena.getArenaName());
	}
}