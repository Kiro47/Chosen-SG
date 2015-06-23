package com.kiro.sg.commands;

import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Leave a game", usage = "", aliases = {"leave"}, op = false)
public class Leave extends GameCommand
{

	public void onCommand(Player p, String[] args)
	{

		//		Arena a = ArenaManager.getInstance().getArena(p);

		GameInstance instance = GameManager.getInstance(p);
		if (instance != null)
		{
			instance.removePlayer(p);
			p.sendMessage(ChatColor.GREEN + "Succefully Left The Game ");
		}
		else
		{
			p.sendMessage(ChatColor.RED + "You are not in a game!");
		}
	}

}
