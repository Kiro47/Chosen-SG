package com.kiro.sg.commands.commands;


import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.lobby.voting.Voting;
import org.bukkit.entity.Player;

@CommandInfo(description = "Selects new maps!", usage = "/newvote", aliases = {"newvote"}, op = true)
public class NewVote extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{
		Voting.reset();
	}

}
