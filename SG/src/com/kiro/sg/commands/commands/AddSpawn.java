package com.kiro.sg.commands.commands;

import com.kiro.sg.game.arena.SGArena;
import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Adds a spawn in an arena!", usage = "<arenaName>", aliases = {"addspawn", "as"}, op = true)
public class AddSpawn extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{

		if (!Meta.has(p, "defineArena"))
		{
			p.sendMessage(ChatColor.RED + "You have to define an arena first! /createarena <world name> <arena name>");
			return;
		}

		SGArena arena = (SGArena) Meta.getMetadata(p, "defineArena");

		//		if (a == null) {
		//			p.sendMessage(ChatColor.RED + "This arena does not exist!");
		//			return;
		//		}
		//
		//		if (!a.getBounds().contains(p.getLocation())) {
		//			p.sendMessage(ChatColor.RED + " That location is outside of the Arena Area!");
		//			return;
		//		}


		arena.addSpawn(p.getLocation());
		//
		//		if (!SettingsManager.getArenas().contains(a.getID() + ".spawns"))
		//		{
		//			SettingsManager.getArenas().createSection(a.getID() + ".spawns");
		//		}
		//
		//		Location loc = new Location(p.getLocation().getWorld(), Math.round(p.getLocation().getX()) + 0.5, Math.round(p.getLocation().getY()), Math.round(p.getLocation().getZ()) + 0.5);
		//
		//		SGMain.saveLocation(loc, SettingsManager.getArenas().createSection(a.getID() + ".spawns" + "." +
		//				                                                                   SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".spawns").getKeys(false).size()));
		//		SettingsManager.getArenas().save();

		p.sendMessage(ChatColor.GREEN + "Added spawn point! :: " + arena.getSpawns().size());
	}

}
