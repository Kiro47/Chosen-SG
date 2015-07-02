package com.kiro.sg.commands.commands;

import com.kiro.sg.PlayerStats;
import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.utils.chat.ChatUtils;
import com.kiro.sg.utils.chat.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Gets a player's stats.", usage = "[name]", aliases = {"stats", "pstats"}, op = false)
public class StatsCommand extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{
		PlayerStats stats = null;
		String name = null;
		if (args.length == 0)
		{
			name = p.getDisplayName();
			stats = PlayerStats.getStats(p);
		}
		else
		{
			name = args[0];
			Player player = Bukkit.getPlayer(name);

			if (player != null)
			{
				stats = PlayerStats.getStats(player);
				name = player.getDisplayName();
			}
			else
			{
				Msg.msgPlayer(p, ChatColor.RED + "Player not found!");
			}
		}

		if (stats != null)
		{
			Msg.msgPlayer(p, ChatColor.GOLD + ChatUtils.fill("-"));

			Msg.msgPlayer(p, ChatColor.YELLOW + ChatUtils.center("    <> " + name + ChatColor.YELLOW + " (" + stats.getID() + ") <>"));
			Msg.msgPlayer(p, "");
			Msg.msgPlayer(p, ChatUtils.format(String.format("        &3Games:&b %03d    &3Kills:    &b%03d", stats.getGames(), stats.getKills())));
			Msg.msgPlayer(p, ChatUtils.format(String.format("        &3Wins:&b   %03d    &3Deaths: &b%03d", stats.getWins(), stats.getDeaths())));

			Msg.msgPlayer(p, ChatColor.GOLD + ChatUtils.fill("-"));
		}
	}

}
