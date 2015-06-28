package com.kiro.sg.commands.commands;

import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import com.kiro.sg.lobby.LobbyManager;
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
			LobbyManager.getInstance().removeFromQueue(p);
			p.sendMessage(ChatColor.GREEN + "Succefully Left The Game Queue");
		}
	}

}
