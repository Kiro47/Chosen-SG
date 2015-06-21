package com.kiro.sg.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;

@CommandInfo (description = "Join a game.", usage= "<ArenaName>", aliases = {"join","ja" }, op = false)
public class Join extends GameCommand{

	public void onCommand(Player p, String[] args) {
		
		if (ArenaManager.getInstance().getArena(p) !=null) {
			p.sendMessage(ChatColor.RED + "You Are Already in a game!");
			return;
		}
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must specify an arena!");
			return;
		}
		
		
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "That arena does not exist!");
			return;
		}
		
		a.addPlayer(p);
		p.sendMessage(ChatColor.GREEN + "Succefully joined Arena " + a.getID());
		
	}
}
