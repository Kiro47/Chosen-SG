package com.kiro.sg.commands;

import com.kiro.sg.arena.SGArena;
import com.kiro.sg.utils.Meta;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandInfo(description = "Create an Arena!", usage = "<world name> <arenaName>", aliases = {"createarena", "ca"}, op = true)
public class CreateArena extends GameCommand
{


	@Override
	public void onCommand(Player p, String[] args)
	{

		if (args.length < 2)
		{
			p.sendMessage(ChatColor.RED + "/createarena <world name> <arena name>!");
			return;
		}

		String worldName = args[0];
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < args.length; i++)
		{
			builder.append(args[i]).append(' ');
		}

		String arenaName = builder.toString().trim();

		SGArena arena = new SGArena(arenaName, worldName);
		arena.createWorld();

		World world = arena.getWorld();
		Location location = new Location(world, 0, 200, 0);
		p.teleport(location);

		Meta.setMetadata(p, "defineArena", arena);

		//String name = args[0];

		//		if (ArenaManager.getInstance().getArena(name) != null)
		//		{
		//			p.sendMessage(ChatColor.RED + "Arena with that name already exists!");
		//			return;
		//		}

		//		Selection s = SGMain.getWorldEdit().getSelection(p);
		//
		//
		//		SettingsManager.getArenas().set(name + ".world", s.getWorld().getName());
		//
		//		SGMain.saveLocation(s.getMinimumPoint(), SettingsManager.getArenas().createSection(name + ".cornerA"));
		//		SGMain.saveLocation(s.getMaximumPoint(), SettingsManager.getArenas().createSection(name + ".cornerB"));
		//
		//		SettingsManager.getArenas().save();
		//
		//		ArenaManager.getInstance().setup();
		//
		//		p.sendMessage(ChatColor.GREEN + "Created arena " + name + ".");
	}
}
