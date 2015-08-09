package com.kiro.sg.commands.commands;

import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.sponsor.menu.SponsorMenu;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Sponsor a player", usage = "sg sponsor <name> <item>", aliases = {"sponsor", "sp"}, op = false)
public class SponsorCommand extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{
//		GameInstance game = GameManager.getInstance(p);
//		if (game == null)
//		{
//			Msg.msgPlayer(p, ChatColor.RED + "You are not in a game!");
//			return;
//		}

		if (args.length == 0)
		{
			SponsorMenu.displayPlayerChoiceMenu(null, p);
		}
		else if (args.length == 1)
		{
			Player player = Bukkit.getPlayer(args[0]);
			if (player != null)
			{
				SponsorMenu.displayItemMenu(null, p, player);
			}
			else
			{
				Msg.msgPlayer(p, ChatColor.RED + "No such player!");
			}
		}
		else if (args.length == 2)
		{
			Player player = Bukkit.getPlayer(args[0]);
			if (player != null)
			{
				int num = Integer.valueOf(args[1]);
				SponsorMenu.process(null, p, player, num);
			}
			else
			{
				Msg.msgPlayer(p, ChatColor.RED + "No such player!");
			}
		}

	}

}