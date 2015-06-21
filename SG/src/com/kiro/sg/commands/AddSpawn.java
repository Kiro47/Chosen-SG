package com.kiro.sg.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;
import com.kiro.sg.SGMain;
import com.kiro.sg.SettingsManager;

@CommandInfo (description = "Adds a spawn in an arena!" , usage = "<arenaName>", aliases = {"addspawn", "as"}, op = true)
public class AddSpawn extends GameCommand{
	
	@Override 
	public void onCommand(Player p, String[] args) {
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must specify to the arena!" );
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "This arena does not exist!");
			return;
		}
		
		if (!a.getBounds().contains(p.getLocation())) {
			p.sendMessage(ChatColor.RED + " That location is outside of the Arena Area!");
			return;
		}
		
		
		a.addSpawn(p.getLocation());
		
		if (!SettingsManager.getArenas().contains(a.getID() + ".spawns")) {
			SettingsManager.getArenas().createSection(a.getID()+".spawns");
		}
		
		Location loc = new Location(p.getLocation().getWorld(), Math.round(p.getLocation().getX()) + 0.5, Math.round(p.getLocation().getY()), Math.round(p.getLocation().getZ()) + 0.5);
		
		SGMain.saveLocation(loc, SettingsManager.getArenas().createSection(a.getID() + ".spawns" + "." + 
				SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".spawns").getKeys(false).size()));
		SettingsManager.getArenas().save();
		
		p.sendMessage(ChatColor.GREEN + "Added spawn at: " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + 
				p.getLocation().getZ() + " in world: " + p.getLocation().getWorld().getName());
	}

}
