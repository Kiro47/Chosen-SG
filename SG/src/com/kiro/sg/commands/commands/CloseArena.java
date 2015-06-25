package com.kiro.sg.commands.commands;

import com.kiro.sg.arena.SGArena;
import com.kiro.sg.commands.CommandInfo;
import com.kiro.sg.commands.GameCommand;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Closes the arena (to stop defining) !", usage = "", aliases = {"closearena", "close"}, op = true)
public class CloseArena extends GameCommand
{
	@Override
	public void onCommand(Player p, String[] args)
	{

		if (!Meta.has(p, "defineArena"))
		{
			p.sendMessage(ChatColor.RED + "You don't have an arena to close");
			return;
		}


		SGArena arena = (SGArena) Meta.getMetadata(p, "defineArena");
		arena.dispose();

		Meta.removeMetadata(p, "defineArena");
	}
}
