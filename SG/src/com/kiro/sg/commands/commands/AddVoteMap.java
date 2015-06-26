package com.kiro.sg.commands.commands;

import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.game.lobby.voting.VotingMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

@CommandInfo(description = "Add a vote map.", usage = "<arenaName>", aliases = {"addmap", "setvote", "setmap"}, op = true)
public class AddVoteMap extends GameCommand
{
	@Override
	public void onCommand(Player player, String[] args)
	{
		Block block = player.getTargetBlock((Set<Material>) null, 10);
		if (block != null)
		{
			try
			{
				player.sendMessage("You have created a Voting Map!");
				VotingMap.defineMap(block.getLocation());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				player.sendMessage("Unable to create Voting Map!");

			}
		}
		player.sendMessage("You have to be looking at a block!");
	}
}
