package com.kiro.sg.commands.commands;

import com.kiro.sg.game.arena.SGArena;
import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "set's the center of the arena", usage = "", aliases = {"center"}, op = true)
public class SetCenter extends GameCommand
{
	@Override
	public void onCommand(Player p, String[] args)
	{

		if (!Meta.has(p, "defineArena"))
		{
			p.sendMessage(ChatColor.RED + "You have to define an arena first! /createarena <world name> <arena name>");
			return;
		}

		SGArena arena = (SGArena) Meta.getMetadata(p, "defineArena");
		arena.setCenterPoint(p.getLocation());

		p.sendMessage(ChatColor.GREEN + "You have set the center point!");
	}
}
