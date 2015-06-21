package com.kiro.sg.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kiro.sg.Arena;
import com.kiro.sg.ArenaManager;

@CommandInfo (description = "Leave a game", usage = "" , aliases = {"leave"}, op = false)
public class Leave extends GameCommand{
	
	public void onCommand(Player p, String[] args) {
		
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (a == null) {
			p.sendMessage(ChatColor.RED + "You are not in a game!");
			return;
		}
		
		a.removePlayer(p);
		p.sendMessage(ChatColor.GREEN + "Succefully Left Arena ");
	}

}
