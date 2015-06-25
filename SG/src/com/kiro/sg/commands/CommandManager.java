package com.kiro.sg.commands;

import com.kiro.sg.commands.commands.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class CommandManager implements CommandExecutor
{

	private final ArrayList<GameCommand> cmds;

	public CommandManager()
	{
		cmds = new ArrayList<GameCommand>();
		cmds.add(new Join());
		cmds.add(new Leave());
		cmds.add(new CreateArena());
		cmds.add(new RemoveArena());
		cmds.add(new AddChest());
		cmds.add(new AddSpawn());
		cmds.add(new ForceStart());
		cmds.add(new SaveArena());
		cmds.add(new AddVoteMap());
		cmds.add(new SetCenter());
		cmds.add(new CloseArena());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return true;
		}

		Player p = (Player) sender;

		if ("survivalgames".equalsIgnoreCase(cmd.getName()))
		{

			if (args.length == 0)
			{
				for (GameCommand gcmd : cmds)
				{
					CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
					p.sendMessage(ChatColor.GOLD + "/survivalgames (" +
							              StringUtils.join(info.aliases(), " ").trim() + ") " + info.usage() + " - " + info.description());
					;
				}
				return true;
			}


			GameCommand wanted = null;

			for (GameCommand gcmd : cmds)
			{
				CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
				for (String alias : info.aliases())
				{
					if (alias.equals(args[0]))
					{
						wanted = gcmd;
						break;
					}
				}
			}

			if (wanted == null)
			{
				p.sendMessage(ChatColor.RED + "Command not found.");
				return true;
			}

			if (wanted.getClass().getAnnotation(CommandInfo.class).op() && !p.isOp())
			{
				p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
				return true;
			}

			ArrayList<String> newArgs = new ArrayList<String>();
			Collections.addAll(newArgs, args);
			newArgs.remove(0);
			args = newArgs.toArray(new String[newArgs.size()]);

			wanted.onCommand(p, args);
		}

		return true;
	}

}
