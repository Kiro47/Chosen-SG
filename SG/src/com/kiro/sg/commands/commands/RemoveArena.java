package com.kiro.sg.commands.commands;

import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kiro.sg.arena.ArenaManager;
import com.kiro.sg.SettingsManager;

@CommandInfo(description = "Remove an arena.", usage = "<name>", aliases = {"removearena", "ra"}, op = true)
public class RemoveArena extends GameCommand
{

	public void onCommand(Player p, String[] args) {
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "This is not a valid Arena");
			return;
		}
		
		String name = args[0];
		
		if (ArenaManager.getInstance().getArena(name) == null) {
			p.sendMessage(ChatColor.RED + "This arena does not exist!");
			return;
		}
		
		SettingsManager.getArenas().set(name, null);
		
		p.sendMessage(ChatColor.GREEN + "Remove Arena: " + name + " this will be applied on restart.");
	}
}
