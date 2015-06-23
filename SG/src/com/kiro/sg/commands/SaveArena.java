package com.kiro.sg.commands;

import com.kiro.sg.arena.SGArena;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Saves the arena!", usage = "<arenaName>", aliases = {"savearena", "sa"}, op = true)
public class SaveArena extends GameCommand
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
		arena.saveArena();
	}
}
