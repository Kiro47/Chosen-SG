package com.kiro.sg.commands.commands;


import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.lobby.leaderboards.LeaderBoard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

@CommandInfo(description = "Adds a sign or skull to a leaderboard!", usage = "<field> <num>", aliases = {"addtop"}, op = true)
public class AddTop extends GameCommand
{
	@Override
	public void onCommand(Player p, String[] args)
	{
		Block target = p.getTargetBlock((Set<Material>) null, 10);
		if (target == null)
		{
			p.sendMessage(ChatColor.RED + "You aren't looking at a skull or sign!");
		}

		Material material = target.getType();
		if (material == Material.WALL_SIGN || material == Material.SIGN || material == Material.SIGN_POST)
		{
			String var = "sign_" + args[1];
			LeaderBoard.addLoc(args[0], var, target.getLocation());
			p.sendMessage(ChatColor.GREEN + String.format("set %s.%s to %s!", args[0], var, target.getLocation().toString()));
		}
		else if (material == Material.SKULL || material == Material.SKULL_ITEM)
		{
			String var = "skull_" + args[1];
			LeaderBoard.addLoc(args[0], var, target.getLocation());
			p.sendMessage(ChatColor.GREEN + String.format("set %s.%s to %s!", args[0], var, target.getLocation().toString()));
		}
		else
		{
			p.sendMessage(ChatColor.RED + "You aren't looking at a skull or sign!");
		}
	}
}
