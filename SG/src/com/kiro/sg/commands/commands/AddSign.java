package com.kiro.sg.commands.commands;

import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.lobby.GameSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

@CommandInfo(description = "Add a game status sign.", usage = "", aliases = {"addsign"}, op = true)
public class AddSign extends GameCommand
{

	@Override
	public void onCommand(Player p, String[] args)
	{
		Block target = p.getTargetBlock((Set<Material>) null, 10);

		if (target == null)
		{
			p.sendMessage(ChatColor.RED + "You are not looking at a block!");
			return;
		}


		// implements crate method
		if (!(target.getState() instanceof Sign))
		{
			p.sendMessage(ChatColor.RED + "You are not looking at a Sign!");
			return;
		}

		GameSign.addSign(target.getLocation());

		p.sendMessage(ChatColor.GREEN + "Added a status sign! ");
	}
}