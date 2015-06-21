package com.kiro.sg.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;


@CommandInfo( description = "Forcibly Starts A Game!", usage = "<ArenaName>", aliases = {"start", "fs"}, op = true)

public class ForceStart extends GameCommand{

	
	@Override
	public void onCommand(Player p, String[] args) {
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must specify an arena to start!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "An arena by that name doesn't exist!");
			return;
		}
		
		a.start();
		p.sendMessage(ChatColor.GREEN + "Arena " + a.getID() + " has been forcibly started!" );
	}
}
