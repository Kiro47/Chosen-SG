package com.kiro.sg.commands.commands;

import com.kiro.sg.SGMain;
import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.game.GameInstance;
import com.kiro.sg.game.GameManager;
import org.bukkit.entity.Player;


@CommandInfo(description = "Forcibly Starts A Game!", usage = "/start", aliases = {"start", "fs"}, op = true)

public class ForceStart extends GameCommand
{


	@Override
	public void onCommand(Player p, String[] args)
	{

		GameInstance instance = GameManager.getInstance(p);
		if (instance != null)
		{
			instance.getGameRunner().setTimer(1);
		}
		else
		{
			SGMain.getPlugin().getLobbyManager().createGame(true);
		}

		//		if (args.length == 0) {
		//			p.sendMessage(ChatColor.RED + "You must specify an arena to start!");
		//			return;
		//		}
		//
		//		Arena a = ArenaManager.getInstance().getArena(args[0]);
		//
		//		if (a == null) {
		//			p.sendMessage(ChatColor.RED + "An arena by that name doesn't exist!");
		//			return;
		//		}
		//
		//		a.start();
		//		p.sendMessage(ChatColor.GREEN + "Arena " + a.getID() + " has been forcibly started!" );
	}
}
